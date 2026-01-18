package com.board.batch.user.service;

import com.board.batch.common.dto.SearchCondition;
import com.board.batch.user.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    List<PostDto> getPosts(SearchCondition searchReq);

    PostDto getPostById(Long id);

    long insertPost(PostDto postDto, MultipartFile[] attachs);

    int editPost(PostDto postDto, MultipartFile[] attachs);

    int deletePost(long id);
}
