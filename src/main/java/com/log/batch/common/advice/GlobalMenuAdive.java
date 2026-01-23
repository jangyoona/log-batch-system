package com.log.batch.common.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalMenuAdive {

    public String activeMenu(HttpServletRequest request) {

        String uri = request.getRequestURI();

        if (uri.startsWith("/posts")) {
            return "posts";
        }

        return "";
    }
}
