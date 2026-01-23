package com.board.batch.user.service.impl;

import com.board.batch.user.dto.PostActionLogDto;
import com.board.batch.user.mapper.PostActionLogMapper;
import com.board.batch.user.service.PostActionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostActionLogServiceImpl implements PostActionLogService {

    private final PostActionLogMapper postActionLogMapper;


    @Override
    public int recordPostAction(PostActionLogDto postActionLog) {
        return postActionLogMapper.insertPostActionLog(postActionLog);
    }

}
