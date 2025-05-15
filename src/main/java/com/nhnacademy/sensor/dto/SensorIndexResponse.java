package com.nhnacademy.sensor.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class SensorIndexResponse {

    private final String gatewayId;

    private final String sensorId;

    @QueryProjection
    public SensorIndexResponse(String gatewayId, String sensorId) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SensorIndexResponse that)) return false;
        return Objects.equals(gatewayId, that.gatewayId)
                && Objects.equals(sensorId, that.sensorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gatewayId, sensorId);
    }

    /// Data Handler가 필요한 Method
    public boolean isNewSensor(String gatewayId, String sensorId) {
        /*return !this.gatewayId.equals(gatewayId)
                || !this.sensorId.equals(sensorId);*/
        return !(this.gatewayId.equals(gatewayId) && this.sensorId.equals(sensorId));
    }
}
