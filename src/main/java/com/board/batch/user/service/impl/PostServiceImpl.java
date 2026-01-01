package com.board.batch.user.service.impl;

import com.board.batch.user.dto.PostDto;
import com.board.batch.user.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public List<PostDto> getPosts() {
        return List.of();
    }
}
