package com.nhnacademy.type.service;

import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.dto.DataTypeInfoResponse;
import com.nhnacademy.type.dto.DataTypeRegisterRequest;
import com.nhnacademy.type.dto.DataTypeUpdateRequest;

public interface DataTypeService {

    void registerRequest(DataTypeRegisterRequest request);

    DataType registerDataType(String dataTypeEnName, String dataTypeKrName);

    DataType getDataType(String dataTypeEnName);

    DataType getReferenceDataType(String dataTypeEnName);

    void updateDataType(DataTypeUpdateRequest request);

    void removeDataType(String dataTypeEnName);

    boolean isExistsDataType(String dataTypeEnName);

    DataTypeInfoResponse getDataTypeInfoResponse(String dataTypeEnName);
}
