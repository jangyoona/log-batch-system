package com.board.batch.user.service;

import com.board.batch.user.dto.PostActionLogDto;

public interface PostActionLogService {

    int recordPostAction(PostActionLogDto postActionLog);
}
