package org.sopt.lequuServer.global.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.sopt.lequuServer.global.common.logging.LoggingMessage;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.sopt.lequuServer.global.exception.enums.ErrorType.*;
import static org.sopt.lequuServer.global.exception.enums.ErrorType.INVALID_JWT_TOKEN;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            if (e.getErrorType().equals(INVALID_JWT_TOKEN)) {
                setErrorResponse(response, INVALID_JWT_TOKEN);
            } else if (e.getErrorType().equals(EXPIRED_JWT_TOKEN)) {
                setErrorResponse(response, EXPIRED_JWT_TOKEN);
            } else if (e.getErrorType().equals(UNSUPPORTED_JWT_TOKEN)) {
                setErrorResponse(response, UNSUPPORTED_JWT_TOKEN);
            } else if (e.getErrorType().equals(EMPTY_JWT_TOKEN)) {
                setErrorResponse(response, EMPTY_JWT_TOKEN);
            } else if (e.getErrorType().equals(INVALID_JWT_SIGNATURE)) {
                setErrorResponse(response, INVALID_JWT_SIGNATURE);
            } else if (e.getErrorType().equals(UNKNOWN_JWT_ERROR)) {
                setErrorResponse(response, UNKNOWN_JWT_ERROR);
            }
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorType errorType) {
        response.setStatus(errorType.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(errorType.getHttpStatusCode(), errorType.getMessage());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            log.error(LoggingMessage.jwtErrorMessage());
            log.error(e.getMessage(), e);
        }
    }

    @Data
    public static class ErrorResponse {
        private final Integer code;
        private final String message;
    }
}
