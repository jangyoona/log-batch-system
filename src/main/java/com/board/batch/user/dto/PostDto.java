package com.board.batch.user.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    private Long id;

    private String userName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private boolean active;

    private List<PostAttachments> attachments;
}
