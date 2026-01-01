package com.board.batch.user.service;

import com.board.batch.user.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getPosts();
}
