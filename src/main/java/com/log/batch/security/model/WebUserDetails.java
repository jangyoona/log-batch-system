package com.log.batch.security.model;

import com.log.batch.user.dto.RoleDto;
import com.log.batch.user.dto.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class WebUserDetails implements UserDetails {

    @Getter
    private UserDto user;
    private RoleDto role;

    public WebUserDetails() {}
    public WebUserDetails(UserDto user) {
        this.user = user;
    }
    public WebUserDetails(UserDto user, RoleDto role) {
        this.user = user;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 권한 목록을 주는 오버라이딩 메서드

        ArrayList<SimpleGrantedAuthority> grants = new ArrayList<>();
        grants.add(new SimpleGrantedAuthority(role.getRoleName()));

        return grants;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    public String getRole() {
        return role.getRoleName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
