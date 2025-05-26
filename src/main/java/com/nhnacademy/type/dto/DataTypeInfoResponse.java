package com.nhnacademy.type.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.type.domain.DataType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class DataTypeInfoResponse {

    @JsonProperty("type_en_name")
    private final String dataTypeEnName;

    @JsonProperty("type_kr_name")
    private final String dataTypeKrName;

    @QueryProjection
    public DataTypeInfoResponse(String dataTypeEnName, String dataTypeKrName) {
        this.dataTypeEnName = dataTypeEnName;
        this.dataTypeKrName = dataTypeKrName;
    }

    public static DataTypeInfoResponse of(String dataTypeEnName, String dataTypeKrName) {
        return new DataTypeInfoResponse(
                dataTypeEnName,
                dataTypeKrName
        );
    }

    public static DataTypeInfoResponse from(DataType dataType) {
        return new DataTypeInfoResponse(
                dataType.getDataTypeEnName(),
                dataType.getDataTypeKrName()
        );
    }
}
