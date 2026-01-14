package com.board.batch.common.controller;


import com.board.batch.common.dto.UserDto;
import com.board.batch.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserDto user) {

        int result = userService.register(user);
        if(result > 0) {
            return "redirect:/login";
        }

        return "register?error";
    }
}
