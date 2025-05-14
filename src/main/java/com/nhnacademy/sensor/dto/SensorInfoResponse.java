package com.nhnacademy.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor.domain.Sensor;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class SensorInfoResponse {

    @JsonProperty("gateway_id")
    private final String gatewayId;

    @JsonProperty("sensor_id")
    private final String sensorId;

    @JsonProperty("sensor_location")
    private final String sensorLocation;

    @JsonProperty("sensor_spot")
    private final String sensorSpot;

    @QueryProjection
    public SensorInfoResponse(String gatewayId, String sensorId, String sensorLocation, String sensorSpot) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.sensorLocation = sensorLocation;
        this.sensorSpot = sensorSpot;
    }

    public static SensorInfoResponse of(String gatewayId, String sensorId, String sensorLocation, String sensorSpot) {
        return new SensorInfoResponse(
                gatewayId,
                sensorId,
                sensorLocation,
                sensorSpot
        );
    }

    public static SensorInfoResponse from(Sensor sensor) {
        return new SensorInfoResponse(
                sensor.getGatewayId(),
                sensor.getSensorId(),
                sensor.getSensorLocation(),
                sensor.getSensorSpot()
        );
    }
}
