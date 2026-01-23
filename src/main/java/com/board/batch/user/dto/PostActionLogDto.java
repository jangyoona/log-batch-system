package com.board.batch.user.dto;

import com.board.batch.common.dto.ActionType;
import com.board.batch.common.dto.BatchStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostActionLogDto {

    private Long id;
    private Long userId;
    private Long postId;
    private ActionType actionType;
    private LocalDateTime actionTime;
    private String ipAddress;
    private String userAgent;
    private BatchStatus status; // 배치 상태


}
