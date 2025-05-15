package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ThresholdHistoryInfo {

    @Valid
    @JsonProperty("sensor_data")
    SensorDataMappingInfo sensorDataMappingInfo;

    @Valid
    @JsonProperty("threshold")
    ThresholdInfo thresholdInfo;

    @Valid
    @JsonProperty("min_range")
    MinRangeInfo minRangeInfo;

    @Valid
    @JsonProperty("max_range")
    MaxRangeInfo maxRangeInfo;

    @Valid
    @JsonProperty("avg_range")
    AvgRangeInfo avgRangeInfo;

    @JsonProperty("delta")
    DeltaInfo deltaInfo;

    @NotNull(message = "계산된 데이터 개수를 전달받지 못했습니다.")
    Integer dataCount;

    @NotNull(message = "계산된 시간을 전달받지 못했습니다.")
    LocalDateTime calculatedAt;
}
