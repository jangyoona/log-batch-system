package com.board.batch.common.aop;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class RequestLoggingAspect {


    @Around("execution(* com.board.batch..controller..*(..))")
    public Object logRequestResponse(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        String userId = getUserIdFromSession(session);


        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();


        Object[] args = joinPoint.getArgs();
        String requestBodyJson = getRequestBodyJson(args);


        log.info("########## [REQUEST] {} {}{} | User: {}",
                method,
                uri,
                (queryString != null && !queryString.isEmpty()) ? "?" + queryString : "",
                userId
        );
        if (!requestBodyJson.isEmpty()) {
            log.info("{}", requestBodyJson);
        }


        // 서버 응답
        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity) {
            ResponseEntity<?> entity = (ResponseEntity<?>) result;
            Object body = entity.getBody();

            int responseCode = entity.getStatusCode().value();

            if (responseCode >= 400) {
                log.error("[RESPONSE] {} {} | User: {}, Status: {}, Data: {} ##########",
                        method, uri, userId, responseCode, body);
            } else {
                log.info("[RESPONSE] {} {} | User: {}, Status: {}, Data: {} ##########",
                        method, uri, userId, responseCode, body);
            }
        }
        return result;


    }


    private String getRequestBodyJson(Object[] args) {

        List<Object> bodyValues = new ArrayList<>();

        for (Object arg : args) {
            if (arg == null) continue;

            // 제외할 타입
            if (arg instanceof HttpServletRequest
                    || arg instanceof HttpServletResponse
                    || arg instanceof HttpSession
                    || arg instanceof BindingResult
                    || arg instanceof String
                    || arg instanceof Number
                    || arg instanceof Boolean
                    || arg.getClass().isPrimitive()) {
                continue;
            }

            // DTO만 추출
            if (arg instanceof Map<?, ?> map) {
                map.values().stream()
                        .filter(v -> !(v instanceof BindingResult)) // BindingResult 제거
                        .forEach(bodyValues::add);
                continue;
            }

            // DTO나 일반 객체
            bodyValues.add(arg);
        }

        // 중복 제거
        bodyValues = bodyValues.stream().distinct().toList();

        // JSON 변환
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        try {
            if (bodyValues.isEmpty()) return "";
//            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bodyValues); // 줄바꿈 (정렬)
            return mapper.writeValueAsString(bodyValues);
        } catch (JsonProcessingException e) {
            return bodyValues.stream().map(Object::toString).collect(Collectors.joining(", "));
        }
    }

    private String getUserIdFromSession(HttpSession session) {
        Object userNameObj = session.getAttribute("userName");
        if (userNameObj != null) {
            return (String) userNameObj;
        } else {
            return null;
        }
    }


}
