package com.log.batch.batch.job.actionlog;

import com.log.batch.actionlog.dto.PostActionLogDailyStatusDto;
import com.log.batch.actionlog.mapper.PostActionLogBatchMapper;
import com.log.batch.post.dto.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ActionLogAggJobConfig {

    private final JobRepository  jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final SqlSessionFactory sqlSessionFactory;
    private final PostActionLogBatchMapper postActionLogBatchMapper;

    @Bean
    public Job actionLogDailyAggJob(
            @Qualifier("actionLogDailyAggStep") Step actionLogDailyAggStep,
            @Qualifier("actionLogMarkDoneStep") Step actionLogMarkDoneStep
    ) {
        return new JobBuilder("actionLogDailyAggJob", jobRepository)
                .start(actionLogDailyAggStep)     // Step1: 집계 UPSERT
                .next(actionLogMarkDoneStep)      // Step2: 원본 DONE 마킹
                .build();
    }


    /** Step1: Reader(집계 SELECT) -> Writer(UPSERT) */
    @Bean(name = "actionLogDailyAggStep")
    public Step actionLogDailyAggStep(
            @Qualifier("dailyStatsReader") ItemReader<PostActionLogDailyStatusDto> reader,
            @Qualifier("dailyStatsWriter") ItemWriter<PostActionLogDailyStatusDto> writer
    ) {
        return new StepBuilder("actionLogDailyAggStep", jobRepository)
                .<PostActionLogDailyStatusDto, PostActionLogDailyStatusDto>chunk(1000, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    /** Step2: 해당 구간 PENDING -> DONE */
    @Bean(name = "actionLogMarkDoneStep")
    public Step actionLogMarkDoneStep(@Qualifier("markDoneTasklet") Tasklet tasklet) {
        return new StepBuilder("actionLogMarkDoneStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }


    /** Reader: "전일 runDate" 구간 PENDING을 GROUP BY로 집계해서 읽음 */
    @Bean(name = "dailyStatsReader")
    @StepScope
    public JdbcCursorItemReader<PostActionLogDailyStatusDto> dailyStatsReader(
            DataSource dataSource,
            @Value("#{jobParameters['runDate']}") String runDate
    ) {
        LocalDate date = LocalDate.parse(runDate);
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();

        String sql = """
        SELECT
            DATE(action_time) AS stat_date,
            post_id           AS post_id,
            action_type       AS action_type,
            COUNT(*)          AS cnt
        FROM post_action_log
        WHERE status = 'PENDING'
          AND action_time >= ?
          AND action_time <  ?
        GROUP BY DATE(action_time), post_id, action_type
        ORDER BY stat_date, post_id, action_type
        """;

        return new JdbcCursorItemReaderBuilder<PostActionLogDailyStatusDto>()
                .name("dailyStatsReader")
                .dataSource(dataSource)
                .sql(sql)
                .preparedStatementSetter(ps -> {
                    ps.setTimestamp(1, Timestamp.valueOf(from));
                    ps.setTimestamp(2, Timestamp.valueOf(to));
                })
                .rowMapper((rs, rowNum) -> PostActionLogDailyStatusDto.builder()
                        .statDate(rs.getDate("stat_date").toLocalDate())
                        .postId(rs.getLong("post_id"))
                        .actionType(ActionType.valueOf(rs.getString("action_type")))
                        .cnt(rs.getLong("cnt"))
                        .build())
                .build();
    }



    /** Writer: 통계 테이블 UPSERT */
    @Bean(name = "dailyStatsWriter")
    ItemWriter<PostActionLogDailyStatusDto> dailyStatsWriter(PostActionLogBatchMapper mapper) {
        return items -> {
            for (PostActionLogDailyStatusDto dto : items) {
                mapper.upsertDailyStats(dto);
            }
        };
    }


    /** Tasklet: 집계가 끝난 구간만 DONE 처리 */
    @Bean(name = "markDoneTasklet")
    @StepScope
    public Tasklet markDoneTasklet(@Value("#{jobParameters['runDate']}") String runDate) {
        return (contribution, chunkContext) -> {
            LocalDate date = LocalDate.parse(runDate);
            LocalDateTime from = date.atStartOfDay();
            LocalDateTime to = date.plusDays(1).atStartOfDay();

            int updated = postActionLogBatchMapper.markDone(from, to);


            log.info(
                    "[BATCH][step=markDoneTasklet][runDate={}] range={} ~ {} | updated={}",
                    runDate, from, to, updated
            );

            return RepeatStatus.FINISHED;
        };
    }





}
