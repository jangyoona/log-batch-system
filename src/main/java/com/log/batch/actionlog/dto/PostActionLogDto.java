package com.log.batch.actionlog.dto;

import com.log.batch.post.dto.ActionType;
import com.log.batch.post.dto.BatchStatus;
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
