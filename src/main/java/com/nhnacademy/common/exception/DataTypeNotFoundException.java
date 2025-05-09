package com.nhnacademy.common.exception;

public class DataTypeNotFoundException extends NotFoundException {

    public DataTypeNotFoundException(String value) {
        super("dataType is not found: %s".formatted(value));
    }
}
