package org.sopt.lequuServer.global.config;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.global.auth.security.CustomJwtAuthenticationEntryPoint;
import org.sopt.lequuServer.global.auth.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;

    private static final String[] AUTH_WHITELIST = {
            "/api/kakao/**", "/loading", "/error", "/api/login", "/api/reissue",
            "/api/test/**", "/health", "/actuator/health",
            "/api/images/**", "/"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(AbstractHttpConfigurer::disable) // Form Login 사용 X
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 사용 X
                .csrf(AbstractHttpConfigurer::disable) // 쿠키 기반이 아닌 JWT 기반이므로 사용 X
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Spring Security 세션 정책 : 세션 생성 및 사용하지 않음
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.authenticationEntryPoint(customJwtAuthenticationEntryPoint))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
