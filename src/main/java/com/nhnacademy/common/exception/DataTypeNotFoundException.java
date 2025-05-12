package com.nhnacademy.common.exception;

public class DataTypeNotFoundException extends NotFoundException {

    public DataTypeNotFoundException(String dataTypeEnName) {
        this(dataTypeEnName, null);
    }

    public DataTypeNotFoundException(String dataTypeEnName, Throwable cause) {
        super("dataType is not found: %s".formatted(dataTypeEnName), cause);
    }
}
