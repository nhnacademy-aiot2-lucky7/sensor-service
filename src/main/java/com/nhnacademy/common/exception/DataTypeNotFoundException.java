package com.nhnacademy.common.exception;

public class DataTypeNotFoundException extends NotFoundException {

    public DataTypeNotFoundException(DataTypeField type, String value) {
        super("dataType is not found: {%s=%s}".formatted(type, value));
    }
}
