package com.nhnacademy.sensor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SensorInfo {

    @NotBlank(message = "게이트웨이 ID가 누락되었습니다.")
    @Size(min = 2, max = 50, message = "게이트웨이 ID는 2자 이상 50자 이하로 입력해야 합니다.")
    @JsonProperty("gateway_id")
    String gatewayId;

    @NotBlank(message = "센서 ID가 누락되었습니다.")
    @Size(min = 2, max = 50, message = "센서 ID는 2자 이상 50자 이하로 입력해야 합니다.")
    @JsonProperty("sensor_id")
    String sensorId;

    @Size(min = 2, max = 30, message = "센서 설치 위치는 30자 이하여야 합니다.")
    @JsonProperty("sensor_location")
    String sensorLocation;

    @Size(min = 2, max = 30, message = "센서 설치 지점은 30자 이하여야 합니다.")
    @JsonProperty("sensor_spot")
    String sensorSpot;
}
