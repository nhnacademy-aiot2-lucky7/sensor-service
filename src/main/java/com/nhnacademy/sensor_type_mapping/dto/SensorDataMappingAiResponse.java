package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SensorDataMappingAiResponse {

    @JsonProperty("gateway_id")
    String gatewayId;

    @JsonProperty("sensor_id")
    String sensorId;

    @JsonProperty("sensor_status")
    SensorStatus sensorStatus;

    @JsonProperty("type_en_name")
    String dataTypeEnName;

    @QueryProjection
    public SensorDataMappingAiResponse(
            String gatewayId, String sensorId,
            SensorStatus sensorStatus, String dataTypeEnName
    ) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.sensorStatus = sensorStatus;
        this.dataTypeEnName = dataTypeEnName;
    }
}
