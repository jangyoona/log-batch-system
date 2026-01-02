package com.board.batch.config.security;

import com.board.batch.common.dto.RoleDto;
import com.board.batch.common.dto.UserDto;
import com.board.batch.common.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        // 데이터베이스에서 데이터 조회
        WebUserDetails userDetails = null;

        Optional<UserDto> result = userMapper.findByUserName(userName); // 이 방식에선 유저Id만 가져오고, Id를 가져온 후 Pw를 체크한다.

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("isUser" + userName);
        }

        UserDto user = result.get();
        RoleDto role = userMapper.findRoleByUserName(userName);
        user.setRole(role);

        return new WebUserDetails(user, role);
    }

    public class UserNotConfirmedException extends UsernameNotFoundException {
        public UserNotConfirmedException(String msg) {
            super(msg);
        }
    }
}
