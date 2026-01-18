package com.board.batch.user.mapper;


import com.board.batch.common.dto.SearchCondition;
import com.board.batch.user.dto.PostAttachments;
import com.board.batch.user.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    int getPostCount(SearchCondition searchReq);

    List<PostDto> getPosts(SearchCondition searchReq);

    PostDto getPostById(Long id);

    int insertPost(PostDto postDto);

    int insertPostAttachments(PostAttachments postAttachments);

    int updatePost(PostDto postDto);

    int deletePost(long id);



}
