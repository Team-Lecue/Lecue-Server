package org.sopt.lequuServer.global.common.exception.model;

import com.sopt.sopkathonServer.common.exception.enums.ErrorType;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorType errorType;

    public BusinessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public int getHttpStatus() {
        return errorType.getHttpStatusCode();
    }
}
