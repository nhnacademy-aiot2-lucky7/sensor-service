package com.nhnacademy.common.exception;

import lombok.Getter;

public class CommonHttpException extends RuntimeException {

    @Getter
    private final int statusCode;

    public CommonHttpException(final int statusCode, final String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public CommonHttpException(final int statusCode, final String message, final Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public CommonHttpException(final CommonHttpException other) {
        super(other.getMessage(), other.getCause());
        this.statusCode = other.getStatusCode();
    }
}
