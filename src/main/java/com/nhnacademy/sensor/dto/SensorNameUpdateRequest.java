package com.nhnacademy.sensor.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/// TODO: valid 로직 추가 예정
@Getter
public final class SensorNameUpdateRequest {

    private final Long gatewayId;

    private final String sensorId;

    private final String sensorName;

    @JsonCreator
    public SensorNameUpdateRequest(
            @JsonProperty("gateway_id") Long gatewayId,
            @JsonProperty("sensor_id") String sensorId,
            @JsonProperty("sensor_name") String sensorName
    ) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.sensorName = sensorName;
    }
}
