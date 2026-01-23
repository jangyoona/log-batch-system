package com.log.batch.security.handler;


import com.log.batch.security.model.WebUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler  implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        WebUserDetails userDetails = (WebUserDetails) authentication.getPrincipal();

        // 로그인 유저 아이디
        HttpSession session = request.getSession();
        session.setAttribute("userName", userDetails.getUser().getUserName());
        session.setAttribute("userRole", userDetails.getRole());

        // 개인회원 Home 이동
        if ("ROLE_USER".equals(userDetails.getRole())) {
            response.sendRedirect("/posts");
            return;
        }

        // 관리자 Home 이동
        response.sendRedirect("/admin/dashboard");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }


}
