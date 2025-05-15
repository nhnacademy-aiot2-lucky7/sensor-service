package com.nhnacademy.sensor_type_mapping.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.Objects;

@Getter
public class SensorDataMappingIndexResponse {

    private final String gatewayId;

    private final String sensorId;

    private final String dataTypeEnName;

    @QueryProjection
    public SensorDataMappingIndexResponse(String gatewayId, String sensorId, String dataTypeEnName) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.dataTypeEnName = dataTypeEnName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SensorDataMappingIndexResponse that)) return false;
        return Objects.equals(gatewayId, that.gatewayId)
                && Objects.equals(sensorId, that.sensorId)
                && Objects.equals(dataTypeEnName, that.dataTypeEnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gatewayId, sensorId, dataTypeEnName);
    }

    /// Data Handler가 필요한 Method
    public boolean isNewInfo(String gatewayId, String sensorId, String dataTypeEnName) {
        return this.gatewayId.equals(gatewayId)
                || this.sensorId.equals(sensorId)
                || this.dataTypeEnName.equals(dataTypeEnName);
    }
}
