package com.log.batch.user.service;

import com.log.batch.user.dto.RoleDto;
import com.log.batch.user.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> findByUserName(String userName);

    RoleDto findRoleByUserName(String userName);

    int register(UserDto userDto);

}
