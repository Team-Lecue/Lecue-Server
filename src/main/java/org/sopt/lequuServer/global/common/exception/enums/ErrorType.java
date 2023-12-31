package org.sopt.lequuServer.global.common.exception.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {

    /**
     * 400 BAD REQUEST
     */
    REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    IMAGE_EXTENSION_ERROR(HttpStatus.BAD_REQUEST, "이미지 확장자는 jpg, png, webp만 가능합니다."),
    IMAGE_SIZE_ERROR(HttpStatus.BAD_REQUEST, "이미지 사이즈는 5MB를 넘을 수 없습니다."),

    /**
     * 500 INTERNAL SERVER ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
