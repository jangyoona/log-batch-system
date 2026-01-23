package com.log.batch.post.mapper;


import com.log.batch.post.dto.SearchCondition;
import com.log.batch.post.dto.PostAttachments;
import com.log.batch.post.dto.PostDto;
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
