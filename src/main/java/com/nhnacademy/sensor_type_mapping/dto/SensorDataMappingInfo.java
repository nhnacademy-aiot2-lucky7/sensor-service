package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor.dto.SensorInfo;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SensorDataMappingInfo {

    @Valid
    @NotNull(message = "센서 정보가 누락되었습니다.")
    @JsonProperty("info")
    SensorInfo sensorInfo;

    @NotBlank(message = "데이터 타입의 영문명이 누락되었습니다.")
    @Size(min = 2, max = 30, message = "데이터 타입의 영문명은 2자 이상 30자 이하로 입력해야 합니다.")
    @JsonProperty("data_type_en_name")
    String dataTypeEnName;

    @JsonProperty("sensor_status")
    SensorStatus sensorStatus;

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
