package com.board.batch.common.service.impl;

import com.board.batch.common.dto.RoleDto;
import com.board.batch.common.dto.UserDto;
import com.board.batch.common.mapper.UserMapper;
import com.board.batch.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<UserDto> findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public RoleDto findRoleByUserName(String userName) {
        return userMapper.findRoleByUserName(userName);
    }

    @Override
    @Transactional
    public int register(UserDto user) {


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int userResult = userMapper.insertUser(user);
        int roleResult = userMapper.insertUserRole(new RoleDto("ROLE_USER", user.getUserName()));

        if (userResult > 0 && roleResult > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
