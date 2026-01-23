package com.log.batch.actionlog.service.impl;

import com.log.batch.actionlog.dto.PostActionLogDto;
import com.log.batch.actionlog.mapper.PostActionLogMapper;
import com.log.batch.actionlog.service.PostActionLogService;
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
