package org.sopt.lequuServer.global.exception.model;

import lombok.Getter;
import org.sopt.lequuServer.global.exception.enums.ErrorType;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorType errorType;

    public CustomException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public int getHttpStatus() {
        return errorType.getHttpStatusCode();
    }
}
