package com.board.batch.common.service;

import com.board.batch.common.dto.RoleDto;
import com.board.batch.common.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> findByUserName(String userName);

    RoleDto findRoleByUserName(String userName);

    int register(UserDto userDto);

}
