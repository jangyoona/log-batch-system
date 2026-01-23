package com.log.batch.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String userName;
    private String password;
    private String repeatPassword;

    private LocalDateTime createdAt;
    private boolean active;

    RoleDto role;


}
