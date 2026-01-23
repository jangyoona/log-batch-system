package com.log.batch.user.dto;

import lombok.Getter;

@Getter
public class RoleDto {

    private Long id;
    private String roleName;
    private String userName;

    public RoleDto(String roleName, String userName) {
        this.roleName = roleName;
        this.userName = userName;
    }
}
