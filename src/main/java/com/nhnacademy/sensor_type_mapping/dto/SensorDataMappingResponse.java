package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.type.dto.DataTypeInfoResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class SensorDataMappingResponse {

    @JsonProperty("sensor")
    private final SensorInfoResponse sensorInfoResponse;

    @JsonProperty("data_type")
    private final DataTypeInfoResponse dataTypeInfoResponse;

    @JsonProperty("sensor_status")
    private final SensorStatus sensorStatus;

    @QueryProjection
    public SensorDataMappingResponse(
            SensorInfoResponse sensorInfoResponse,
            DataTypeInfoResponse dataTypeInfoResponse,
            SensorStatus sensorStatus
    ) {
        this.sensorInfoResponse = sensorInfoResponse;
        this.dataTypeInfoResponse = dataTypeInfoResponse;
        this.sensorStatus = sensorStatus;
    }
}
