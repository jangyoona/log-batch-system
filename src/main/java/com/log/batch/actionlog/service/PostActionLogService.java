package com.log.batch.actionlog.service;

import com.log.batch.actionlog.dto.PostActionLogDto;

public interface PostActionLogService {

    int recordPostAction(PostActionLogDto postActionLog);
}
