package com.nhnacademy.sensor.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public final class SensorInfo {

    @NotNull(message = "게이트웨이 ID가 누락되었습니다.")
    @Positive(message = "게이트웨이 ID는 (2^0) 이상으로 입력해야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "게이트웨이 ID는 (2^63 - 1) 이하로 입력해야 합니다.")
    private final Long gatewayId;

    @NotBlank(message = "센서 ID가 누락되었습니다.")
    @Size(min = 2, max = 100, message = "센서 ID는 2자 이상 100자 이하로 입력해야 합니다.")
    private final String sensorId;

    @Size(min = 1, max = 50, message = "센서 설치 위치는 1자 이상 50자 이하여야 합니다.")
    private final String sensorLocation;

    @Size(min = 1, max = 50, message = "센서 설치 지점은 1자 이상 50자 이하여야 합니다.")
    private final String sensorSpot;

    @JsonCreator
    public SensorInfo(
            @JsonProperty("gateway_id") Long gatewayId,
            @JsonProperty("sensor_id") String sensorId,
            @JsonProperty("sensor_location") String sensorLocation,
            @JsonProperty("sensor_spot") String sensorSpot
    ) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.sensorLocation = sensorLocation;
        this.sensorSpot = sensorSpot;
    }
}
