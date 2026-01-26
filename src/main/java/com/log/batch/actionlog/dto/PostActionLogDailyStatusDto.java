package com.log.batch.actionlog.dto;

import com.log.batch.post.dto.ActionType;
import lombok.*;

import java.time.LocalDate;

/**
 * 일별 액션 로그 집계 결과 DTO
 * - Reader(집계 SELECT) 결과를 담고
 * - Writer(UPSERT) 파라미터로 사용
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostActionLogDailyStatusDto {

    private LocalDate statDate;      // 집계 기준일
    private ActionType actionType;  // 액션 타입
    private Long postId;            // 게시글 pk
    private long cnt;               // 발생 건수

}
