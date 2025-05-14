package com.nhnacademy.sensor_type_mapping.dto;

import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import lombok.Value;

@Value
public class SensorDataMappingSearchRequest {

    String gatewayId;

    String sensorId;

    String sensorLocation;

    String sensorSpot;

    String dataTypeEnName;

    SensorStatus sensorStatus;

    public boolean isNotNullGatewayId() {
        return gatewayId != null && !gatewayId.isBlank();
    }

    public boolean isNotNullSensorId() {
        return sensorId != null && !sensorId.isBlank();
    }

    public boolean isNotNullSensorLocation() {
        return sensorLocation != null && !sensorLocation.isBlank();
    }

    public boolean isNotNullSensorSpot() {
        return sensorSpot != null && !sensorSpot.isBlank();
    }

    public boolean isNotNullDataTypeEnName() {
        return dataTypeEnName != null && !dataTypeEnName.isBlank();
    }

    public boolean isNotNullSensorStatus() {
        return sensorStatus != null;
    }
}
