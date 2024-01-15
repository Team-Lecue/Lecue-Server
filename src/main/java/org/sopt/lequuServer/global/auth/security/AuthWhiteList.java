package org.sopt.lequuServer.global.auth.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AuthWhiteList {

    public static final List<String> AUTH_WHITELIST_DEFALUT = Arrays.asList(
            "/loading", "/error", "/api/login", "/api/reissue",
            "/health", "/actuator/health", "/"
    );

    public static final List<String> AUTH_WHITELIST_WILDCARD = Arrays.asList(
            "/api/kakao/**", "/api/test/**", "/api/images/**",
            "/swagger-ui/**", "/swagger-resources/**", "/api-docs/**",
            "/api/common/**", "/api/books/detail/**"
    );

    public static final String[] AUTH_WHITELIST = Stream.concat(
            AUTH_WHITELIST_DEFALUT.stream(),
            AUTH_WHITELIST_WILDCARD.stream()
    ).toArray(String[]::new);
}
