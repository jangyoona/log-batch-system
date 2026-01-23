package com.board.batch.user.service;

import com.board.batch.common.dto.SearchCondition;
import com.board.batch.user.dto.PostDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    List<PostDto> getPosts(SearchCondition searchReq);

    PostDto getPostById(Long id);

    long createPost(PostDto postDto, MultipartFile[] attachs, HttpServletRequest request);

    int editPost(PostDto postDto, MultipartFile[] attachs, HttpServletRequest request);

    int deletePost(long id, Long userId, HttpServletRequest request);
}
