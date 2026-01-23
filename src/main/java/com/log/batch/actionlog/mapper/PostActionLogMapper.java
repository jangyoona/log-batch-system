package com.log.batch.actionlog.mapper;

import com.log.batch.actionlog.dto.PostActionLogDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostActionLogMapper {

    int insertPostActionLog(PostActionLogDto postActionLog);
}
