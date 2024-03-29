package org.sopt.lequuServer.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.enums.SuccessType;

@Getter
@JsonPropertyOrder({"code", "message", "data"})
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static ApiResponse<?> success(SuccessType successType) {
        return new ApiResponse<>(successType.getHttpStatusCode(), successType.getMessage());
    }

    public static <T> ApiResponse<T> success(SuccessType successType, T data) {
        return new ApiResponse<T>(successType.getHttpStatusCode(), successType.getMessage(), data);
    }

    public static ApiResponse<?> error(ErrorType errorType) {
        return new ApiResponse<>(errorType.getHttpStatusCode(), errorType.getMessage());
    }

    public static <T> ApiResponse<T> error(ErrorType errorType, T data) {
        return new ApiResponse<>(errorType.getHttpStatusCode(), errorType.getMessage(), data);
    }

    public static <T> ApiResponse<Exception> error(ErrorType errorType, Exception e) {
        return new ApiResponse<>(errorType.getHttpStatusCode(), errorType.getMessage(), e);
    }

    public static <T> ApiResponse<Exception> error(ErrorType errorType, String message, Exception e) {
        return new ApiResponse<>(errorType.getHttpStatusCode(), message, e);
    }
}