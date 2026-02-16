package com.sisuz.pos.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class DomainException extends RuntimeException {

    private final int code;
    private final HttpStatus status;

    protected DomainException(int code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }

}
