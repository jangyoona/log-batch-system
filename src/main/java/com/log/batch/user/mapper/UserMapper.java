package com.log.batch.user.mapper;

import com.log.batch.user.dto.RoleDto;
import com.log.batch.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<UserDto> findByUserName(String userName);

    RoleDto findRoleByUserName(String userName);

    int insertUser(UserDto userDto);

    int insertUserRole(RoleDto userDto);
}
