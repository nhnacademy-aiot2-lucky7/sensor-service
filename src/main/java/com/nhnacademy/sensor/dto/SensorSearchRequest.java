package com.nhnacademy.sensor.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SensorSearchRequest {

    @Size(max = 50, message = "게이트웨이 ID는 2자 이상 50자 이하로 입력해야 합니다.")
    String gatewayId;

    @Size(max = 50, message = "센서 ID는 2자 이상 50자 이하로 입력해야 합니다.")
    String sensorId;

    @Size(max = 30, message = "센서 설치 위치는 30자 이하여야 합니다.")
    String sensorLocation;

    @Size(max = 30, message = "센서 설치 지점은 30자 이하여야 합니다.")
    String sensorSpot;

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
}
