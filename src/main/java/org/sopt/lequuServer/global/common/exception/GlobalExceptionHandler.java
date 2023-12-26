package com.sopt.sopkathonServer.common.exception;

import com.sopt.sopkathonServer.common.dto.ApiResponse;
import com.sopt.sopkathonServer.common.exception.model.BusinessException;
import com.sopt.sopkathonServer.common.exception.slack.SlackUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sopt.sopkathonServer.common.exception.enums.ErrorType.INTERNAL_SERVER_ERROR;
import static com.sopt.sopkathonServer.common.exception.enums.ErrorType.REQUEST_VALIDATION_EXCEPTION;


@Slf4j
@RestControllerAdvice
@Component
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SlackUtil slackUtil;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {

        Errors errors = e.getBindingResult();
        Map<String, String> validateDetails = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validateDetails.put(validKeyName, error.getDefaultMessage());
        }
        return ApiResponse.error(REQUEST_VALIDATION_EXCEPTION, validateDetails);
    }

    /**
     * CUSTOM_ERROR
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {

        log.error("BusinessException occured: {}", e.getMessage(), e);

        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponse.error(e.getErrorType(), e.getMessage()));
    }

    /**
     * 500 INTERNEL_SERVER
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ApiResponse<Exception> handleException(final Exception e, final HttpServletRequest request) throws IOException {
        slackUtil.sendAlert(e, request);
        return ApiResponse.error(INTERNAL_SERVER_ERROR, e);
    }
}