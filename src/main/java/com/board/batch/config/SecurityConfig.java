package com.board.batch.config;

import com.board.batch.config.security.WebUserDetailsService;
import com.board.batch.config.security.handler.LoginSuccessHandler;
import com.board.batch.config.security.handler.LogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    private final WebUserDetailsService webUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 개발
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()
//                )
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable);

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/static/**", "/assets/**").permitAll()
                        .requestMatchers("/login", "/register","/login/denied").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/posts/new", "/posts/*/edit", "/posts/*/delete").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()) // 그 외 모든 비허용
                .exceptionHandling(configurer -> configurer.accessDeniedHandler(accessDeniedHandler())) // 권한 부족시 error 페이지로 이동
                .httpBasic(AbstractHttpConfigurer::disable)
                .userDetailsService(webUserDetailsService)
                .formLogin((login) -> login
                        .loginPage("/login") // form
                        .loginProcessingUrl("/login") // 호출 action
                        .successHandler(loginSuccessHandler)
                        .failureUrl("/login?error=1")
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/login")
                )

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
        // remember-me
//        http.rememberMe( rememberMe -> rememberMe
//                .key(rememberMeKey)
//                .tokenRepository(persistentTokenRepository)
//                .tokenValiditySeconds(60 * 60 * 24 * 14) // 토큰 유효기간 14일
//                .authenticationSuccessHandler(loginSuccessHandler)
//                .alwaysRemember(false)
//                .userDetailsService(userDetailsService)
//        );

        return http.build();
    }

    private AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendRedirect("/home"); // Redirect to your custom URL
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // 권한 부족 시 리다이렉트
            response.sendRedirect("/error");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
