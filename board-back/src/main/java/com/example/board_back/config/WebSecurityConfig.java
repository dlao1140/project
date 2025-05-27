package com.example.board_back.config;

import java.beans.BeanProperty;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.board_back.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        .cors(cors -> cors
            .configurationSource(corsConfigrationSource())
            )
            .csrf(CsrfConfigurer::disable)
            .httpBasic(HttpBasicConfigurer::disable)
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(request -> request
                .requestMatchers(
                    "/", 
                    "/api/v1/auth/**", 
                    "/api/v1/search/**", 
                    "/api/v1/board/latest-list", 
                    "/api/v1/board/top-3",
                    "/api/v1/board/*", // 👈 게시글 상세
                    "/api/v1/board/search-list/**",
                    "/api/v1/board/*/favorite-list", // 👈 좋아요
                    "/api/v1/board/*/comment-list",  // 👈 댓글
                    "/api/v1/board/*/increase-view-count", // 👈 조회수 증가
                    "/api/v1/user/**", // ✨ 사용자 정보 조회 경로 추가 (GET 요청에 대해)
                    "/file/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exceptionHandleing -> exceptionHandleing
                .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            
        
        return httpSecurity.build();
    }
    // @Bean
    // protected CorsConfigurationSource corsConfigrationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     // configuration.addAllowedOrigin("*");
    //     // configuration.addAllowedMethod("*");
    //     // configuration.addExposedHeader("*");
    //     configuration.setAllowedOrigins(List.of("http://localhost:3001"));
    //     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    //     configuration.setAllowedHeaders(List.of("*"));
    //     configuration.setExposedHeaders(List.of("*"));
    //     configuration.setAllowCredentials(true);
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;
         
    // }
    @Bean
    protected CorsConfigurationSource corsConfigrationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(List.of("http://localhost:3001")); // 프론트 주소
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); // PATCH 꼭 포함
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // ✔ 쿠키 포함 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Authorization Failed.\" }");
        response.getWriter().flush();
    }
    
}
