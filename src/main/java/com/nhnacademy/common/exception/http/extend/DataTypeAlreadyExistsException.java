package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.ConflictException;
import com.nhnacademy.type.dto.DataTypeRegisterRequest;

public class DataTypeAlreadyExistsException extends ConflictException {

    public DataTypeAlreadyExistsException(DataTypeRegisterRequest request) {
        this(request.getDataTypeEnName(), null);
    }

    public DataTypeAlreadyExistsException(String dataTypeEnName) {
        this(dataTypeEnName, null);
    }

    public DataTypeAlreadyExistsException(String dataTypeEnName, Throwable cause) {
        super("dataType already exists: %s".formatted(dataTypeEnName), cause);
    }
}
