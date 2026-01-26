package com.log.batch.actionlog.mapper;

import com.log.batch.actionlog.dto.PostActionLogDailyStatusDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface PostActionLogBatchMapper {

    PostActionLogDailyStatusDto selectDailyStats(PostActionLogDailyStatusDto dto);

    int upsertDailyStats(PostActionLogDailyStatusDto dto);

    int markDone(LocalDateTime from, LocalDateTime to);
}
