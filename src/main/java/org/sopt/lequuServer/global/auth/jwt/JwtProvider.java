package org.sopt.lequuServer.global.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.global.auth.redis.RefreshToken;
import org.sopt.lequuServer.global.auth.redis.TokenRepository;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;
import java.util.Date;

import static java.util.Objects.isNull;
import static org.sopt.lequuServer.global.exception.enums.ErrorType.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 1000L * 60 * 24 * 365;  // 액세스 토큰 만료 시간: 1년으로 지정
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 1000L * 2 * 60 * 24 * 365;  // 리프레시 토큰 만료 시간: 2년으로 지정

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    private final TokenRepository tokenRepository;

    @PostConstruct
    protected void init() {
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Access 토큰, Refresh 토큰 발급
    public TokenDto issueToken(Authentication authentication) {
        return TokenDto.of(
                generateAccessToken(authentication),
                generateRefreshToken(authentication));
    }

    // Access 토큰 생성
    private String generateAccessToken(Authentication authentication) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME));

        claims.put("memberId", authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    // Refresh 토큰 생성

    /**
     * Redis 내부에
     * memberId: refreshToken 형태로 저장
     */
    private String generateRefreshToken(Authentication authentication) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME));

        claims.put("memberId", authentication.getPrincipal());

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey())
                .compact();

        tokenRepository.save(
                RefreshToken.builder()
                        .memberId(Long.parseLong(authentication.getPrincipal().toString()))
                        .refreshToken(refreshToken)
                        .expiration(REFRESH_TOKEN_EXPIRATION_TIME.intValue() / 1000)
                        .build()
        );

        return refreshToken;
    }

    // Access 토큰 검증
    public JwtValidationType validateAccessToken(String accessToken) {
        try {
            final Claims claims = getBody(accessToken);
            log.info(JwtValidationType.VALID_JWT.getValue());
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            log.warn(JwtValidationType.INVALID_JWT_TOKEN.getValue());
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            log.warn(JwtValidationType.EXPIRED_JWT_TOKEN.getValue());
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            log.warn(JwtValidationType.UNSUPPORTED_JWT_TOKEN.getValue());
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            log.warn(JwtValidationType.EMPTY_JWT.getValue());
            return JwtValidationType.EMPTY_JWT;
        }
    }

    // Refresh 토큰 검증
    public Long validateRefreshToken(String refreshToken) {
        // Refresh 토큰 만료 : Redis에 해당 Refresh 토큰이 존재하지 않음
        Long memberId = getUserFromJwt(refreshToken);
        if (tokenRepository.existsById(memberId)) {
            return memberId;
        } else {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }
    }

    // Refresh 토큰 삭제 (memberId 기준으로)
    public void deleteRefreshToken(Long memberId) {
        if (tokenRepository.existsById(memberId)) {
            tokenRepository.deleteById(memberId);
        } else {
            throw new CustomException(NOT_FOUND_REFRESH_TOKEN_ERROR);
        }
    }

    // 토큰에 담겨있는 memberId 획득
    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.parseLong(claims.get("memberId").toString());
    }

    private Claims getBody(final String token) {
        // 만료된 토큰에 대해 parseClaimsJws를 수행하면 io.jsonwebtoken.ExpiredJwtException이 발생
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    public static Long getUserFromPrincial(Principal principal) {
        if (isNull(principal)) {
            throw new CustomException(EMPTY_PRINCIPLE_ERROR);
        }

        return Long.valueOf(principal.getName());
    }
}