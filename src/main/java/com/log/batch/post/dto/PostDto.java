package com.log.batch.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDto {

    private Long id;

    private Long userId;
    private String userName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private boolean active;

    private List<PostAttachments> attachments;
}
