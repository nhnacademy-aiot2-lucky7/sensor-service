package com.nhnacademy.sensor_type_mapping.dto;

import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SensorDataMappingUpdateRequest {

    @NotBlank(message = "게이트웨이 ID가 누락되었습니다.")
    @Size(min = 2, max = 50, message = "게이트웨이 ID는 2자 이상 50자 이하로 입력해야 합니다.")
    String gatewayId;

    @NotBlank(message = "센서 ID가 누락되었습니다.")
    @Size(min = 2, max = 50, message = "센서 ID는 2자 이상 50자 이하로 입력해야 합니다.")
    String sensorId;

    @NotBlank(message = "센서 측정 데이터의 영문 타입명이 누락되었습니다.")
    @Size(min = 2, max = 30, message = "영문 타입명은 2자 이상 30자 이하로 입력해야 합니다.")
    String dataTypeEnName;

    @NotNull(message = "센서 상태 값이 누락되었습니다.")
    SensorStatus sensorStatus;
}
