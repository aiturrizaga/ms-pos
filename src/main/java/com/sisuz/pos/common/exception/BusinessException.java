package com.sisuz.pos.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends DomainException {

    public BusinessException(String message) {
        super(422, HttpStatus.BAD_REQUEST, message);
    }

    public BusinessException(int code, String message) {
        super(code, HttpStatus.BAD_REQUEST, message);
    }

    public BusinessException(int code, HttpStatus status, String message) {
        super(code, status, message);
    }
}
