package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor.dto.SensorInfo;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public final class SensorDataMappingInfo {

    @Valid
    @NotNull(message = "센서 정보가 누락되었습니다.")
    private final SensorInfo sensorInfo;

    @NotBlank(message = "데이터 타입의 영문명이 누락되었습니다.")
    @Size(min = 2, max = 50, message = "데이터 타입의 영문명은 2자 이상 50자 이하로 입력해야 합니다.")
    private final String dataTypeEnName;

    private final SensorStatus sensorStatus;

    @JsonCreator
    public SensorDataMappingInfo(
            @JsonProperty("info") SensorInfo sensorInfo,
            @JsonProperty("type_en_name") String dataTypeEnName,
            @JsonProperty("sensor_status") SensorStatus sensorStatus
    ) {
        this.sensorInfo = sensorInfo;
        this.dataTypeEnName = dataTypeEnName;
        this.sensorStatus = sensorStatus;
    }

    public String getGatewayId() {
        return sensorInfo.getGatewayId();
    }

    public String getSensorId() {
        return sensorInfo.getSensorId();
    }

    public String getSensorLocation() {
        return sensorInfo.getSensorLocation();
    }

    public String getSensorSpot() {
        return sensorInfo.getSensorSpot();
    }
}
