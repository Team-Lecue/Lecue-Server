package org.sopt.lequuServer.global.config;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.global.auth.security.JwtAuthenticationFilter;
import org.sopt.lequuServer.global.auth.security.JwtExceptionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    public static final List<String> AUTH_WHITELIST = Arrays.asList(
            "/api/kakao/**", "/loading", "/error", "/api/login", "/api/reissue",
            "/api/test/**", "/health", "/actuator/health",
            "/api/images/**", "/", "/swagger-ui/**", "/swagger-resources/**", "/api-docs/**",
            "/api/common/**", "/api/books/detail/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(AbstractHttpConfigurer::disable) // Form Login 사용 X
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 사용 X
                .csrf(AbstractHttpConfigurer::disable) // 쿠키 기반이 아닌 JWT 기반이므로 사용 X
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Spring Security 세션 정책 : 세션 생성 및 사용하지 않음
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(AUTH_WHITELIST.toArray(new String[0])).permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .build();
    }
}
