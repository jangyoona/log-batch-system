package com.board.batch.user.mapper;

import com.board.batch.user.dto.PostActionLogDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostActionLogMapper {

    int insertPostActionLog(PostActionLogDto postActionLog);
}
