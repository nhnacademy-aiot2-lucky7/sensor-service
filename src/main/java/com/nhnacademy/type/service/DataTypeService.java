package com.nhnacademy.type.service;

import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.dto.DataTypeInfo;
import com.nhnacademy.type.dto.DataTypeInfoResponse;

public interface DataTypeService {

    void registerRequest(DataTypeInfo request);

    DataType registerDataType(String dataTypeEnName, String dataTypeKrName);

    DataType getDataType(String dataTypeEnName);

    DataType getReferenceDataType(String dataTypeEnName);

    void updateDataType(DataTypeInfo request);

    void removeDataType(String dataTypeEnName);

    boolean isExistsDataType(String dataTypeEnName);

    DataTypeInfoResponse getDataTypeInfoResponse(String dataTypeEnName);
}
