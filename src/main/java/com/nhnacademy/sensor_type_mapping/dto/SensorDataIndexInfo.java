package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nhnacademy.common.jackson.SensorStatusDeserializer;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public final class SensorDataIndexInfo {

    @NotNull(message = "게이트웨이 ID가 누락되었습니다.")
    @Positive(message = "게이트웨이 ID는 (2^0) 이상으로 입력해야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "게이트웨이 ID는 (2^63 - 1) 이하로 입력해야 합니다.")
    private final Long gatewayId;

    @NotBlank(message = "센서 ID가 누락되었습니다.")
    @Size(min = 2, max = 100, message = "센서 ID는 2자 이상 100자 이하로 입력해야 합니다.")
    private final String sensorId;

    @NotBlank(message = "데이터 타입의 영문명이 누락되었습니다.")
    @Size(min = 2, max = 50, message = "데이터 타입의 영문명은 2자 이상 50자 이하로 입력해야 합니다.")
    private final String typeEnName;

    private final SensorStatus sensorStatus;

    @JsonCreator
    public SensorDataIndexInfo(
            @JsonProperty("gateway_id") Long gatewayId,
            @JsonProperty("sensor_id") String sensorId,
            @JsonProperty("type_en_name") String typeEnName,
            @JsonProperty("status")
            @JsonDeserialize(using = SensorStatusDeserializer.class) SensorStatus sensorStatus
    ) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.typeEnName = typeEnName;
        this.sensorStatus = sensorStatus;
    }
}
