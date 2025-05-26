package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SearchNoRequest {

    @NotBlank(message = "게이트웨이 ID가 누락되었습니다.")
    @Size(min = 2, max = 100, message = "게이트웨이 ID는 2자 이상 100자 이하로 입력해야 합니다.")
    @JsonProperty("gateway_id")
    String gatewayId;

    @NotBlank(message = "센서 ID가 누락되었습니다.")
    @Size(min = 2, max = 100, message = "센서 ID는 2자 이상 100자 이하로 입력해야 합니다.")
    @JsonProperty("sensor_id")
    String sensorId;

    @NotBlank(message = "데이터 타입의 영문명이 누락되었습니다.")
    @Size(min = 2, max = 50, message = "데이터 타입의 영문명은 2자 이상 50자 이하로 입력해야 합니다.")
    @JsonProperty("type_en_name")
    String dataTypeEnName;
}
