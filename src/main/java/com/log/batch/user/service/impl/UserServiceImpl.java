package com.log.batch.user.service.impl;

import com.log.batch.user.dto.RoleDto;
import com.log.batch.user.dto.UserDto;
import com.log.batch.user.mapper.UserMapper;
import com.log.batch.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
