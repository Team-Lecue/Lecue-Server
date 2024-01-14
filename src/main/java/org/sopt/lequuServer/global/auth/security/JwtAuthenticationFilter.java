package org.sopt.lequuServer.global.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.sopt.lequuServer.global.auth.security.AuthWhiteList.AUTH_WHITELIST_DEFALUT;
import static org.sopt.lequuServer.global.auth.security.AuthWhiteList.AUTH_WHITELIST_WILDCARD;

/**
 * JWT의 유효성을 검증하는 Filter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException, IOException {

        if (AUTH_WHITELIST_DEFALUT.stream()
                .anyMatch(whiteUrl -> request.getRequestURI().equals(whiteUrl))) {
            filterChain.doFilter(request, response);
            return;
        }

        if (AUTH_WHITELIST_WILDCARD.stream()
                .anyMatch(whiteUrl -> request.getRequestURI().startsWith(whiteUrl.substring(0, whiteUrl.length() - 3)))) {
            filterChain.doFilter(request, response);
            return;
        }

        // Request의 Header에서 JWT 토큰을 String으로 가져옴
        final String token = getJwtFromRequest(request);
        if (jwtProvider.validateAccessToken(token)) {
            Long memberId = jwtProvider.getUserFromJwt(token);
            UserAuthentication authentication = new UserAuthentication(memberId, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}