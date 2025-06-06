package com.nhnacademy.sensor.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/// TODO: valid 로직 추가 예정
@Getter
public final class SensorPositionUpdateRequest {

    private final Long gatewayId;

    private final String sensorId;

    private final String location;

    private final String spot;

    @JsonCreator
    public SensorPositionUpdateRequest(
            @JsonProperty("gateway_id") Long gatewayId,
            @JsonProperty("sensor_id") String sensorId,
            @JsonProperty("location") String location,
            @JsonProperty("spot") String spot
    ) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.location = location;
        this.spot = spot;
    }
}
