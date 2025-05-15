package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.ConflictException;

public class DataTypeAlreadyExistsException extends ConflictException {

    public DataTypeAlreadyExistsException(String dataTypeEnName) {
        this(dataTypeEnName, null);
    }

    public DataTypeAlreadyExistsException(String dataTypeEnName, Throwable cause) {
        super("dataType already exists: %s".formatted(dataTypeEnName), cause);
    }
}
