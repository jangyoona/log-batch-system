package com.board.batch.common.mapper;

import com.board.batch.common.dto.RoleDto;
import com.board.batch.common.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<UserDto> findByUserName(String userName);

    RoleDto findRoleByUserName(String userName);

    int insertUser(UserDto userDto);

    int insertUserRole(RoleDto userDto);
}
