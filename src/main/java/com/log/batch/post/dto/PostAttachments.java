package com.log.batch.post.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostAttachments {

    private Long id;
    private Long postId;

    private String originName;
    private String storedName;
    private LocalDateTime createdAt;

}
