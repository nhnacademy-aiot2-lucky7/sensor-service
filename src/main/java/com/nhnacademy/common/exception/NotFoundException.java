package com.nhnacademy.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CommonHttpException {

    private static final int HTTP_STATUS_CODE = HttpStatus.NOT_FOUND.value();

    public NotFoundException() {
        super(HTTP_STATUS_CODE, "resource not found");
    }

    public NotFoundException(final String message) {
        super(HTTP_STATUS_CODE, message);
    }

    public NotFoundException(final String message, final Throwable cause) {
        super(HTTP_STATUS_CODE, message, cause);
    }
}
