package org.sopt.lequuServer.global.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.config.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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

        if (SecurityConfig.AUTH_WHITELIST.stream()
                .anyMatch(whiteUrl -> {
                    String modifiedWhiteUrl = whiteUrl.endsWith("/**") ? whiteUrl.substring(0, whiteUrl.length() - 3) : whiteUrl;
                    return request.getRequestURI().contains(modifiedWhiteUrl);
                })) {
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