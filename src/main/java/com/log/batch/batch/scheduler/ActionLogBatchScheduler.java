package com.log.batch.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ActionLogBatchScheduler {

    private final JobLauncher jobLauncher;

    @Qualifier("actionLogDailyAggJob")
    private final Job actionLogDailyAggJob;

    /** 매일 01:00 전일 로그 집계 */
    @Scheduled(cron = "0 0 1 * * *")
    public void runDaily() throws Exception {
        String runDate = LocalDate.now().minusDays(1).toString(); // 전일
        
        JobParameters params = new JobParametersBuilder()
                .addString("runDate", runDate)
                .addLong("ts", System.currentTimeMillis()) // 같은 runDate 재실행 가능하게
                .toJobParameters();

        jobLauncher.run(actionLogDailyAggJob, params);
    }

}
