package com.board.batch.user.mapper;


import com.board.batch.user.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    List<PostDto> getPosts(String userName, int page, int size);



}
