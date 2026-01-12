package com.board.batch.user.service;

import com.board.batch.user.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    List<PostDto> getPosts();

    int insertPost(PostDto postDto, MultipartFile[] attachs);
}
