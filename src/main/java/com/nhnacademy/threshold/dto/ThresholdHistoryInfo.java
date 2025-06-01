package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataIndexInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.ToString;

/// TODO: Valid message 양식을 다듬기
@Getter
@ToString
public final class ThresholdHistoryInfo {

    @Valid
    @NotNull(message = "sensor_info 매칭 실패")
    private final SensorDataIndexInfo sensorDataIndexInfo;

    @Valid
    @NotNull(message = "threshold 매칭 실패")
    private final ThresholdInfo thresholdInfo;

    @Valid
    @NotNull(message = "min_range 매칭 실패")
    private final MinRangeInfo minRangeInfo;

    @Valid
    @NotNull(message = "max_range 매칭 실패")
    private final MaxRangeInfo maxRangeInfo;

    @Valid
    @NotNull(message = "avg_range 매칭 실패")
    private final AvgRangeInfo avgRangeInfo;

    @Valid
    private final DiffInfo diffInfo;

    @NotNull
    @PositiveOrZero
    private final Integer dataCount;

    @NotNull
    @Positive(message = "잘못된 시간 형식입니다.")
    private final Long calculatedAt;

    @JsonCreator
    public ThresholdHistoryInfo(
            @JsonProperty("sensor_info") SensorDataIndexInfo sensorDataIndexInfo,
            @JsonProperty("threshold") ThresholdInfo thresholdInfo,
            @JsonProperty("min_range") MinRangeInfo minRangeInfo,
            @JsonProperty("max_range") MaxRangeInfo maxRangeInfo,
            @JsonProperty("avg_range") AvgRangeInfo avgRangeInfo,
            @JsonProperty("diff") DiffInfo diffInfo,
            @JsonProperty("data_count") Integer dataCount,
            @JsonProperty("calculated_at") Long calculatedAt
    ) {
        this.sensorDataIndexInfo = sensorDataIndexInfo;
        this.thresholdInfo = thresholdInfo;
        this.minRangeInfo = minRangeInfo;
        this.maxRangeInfo = maxRangeInfo;
        this.avgRangeInfo = avgRangeInfo;
        this.diffInfo = diffInfo;
        this.dataCount = dataCount;
        this.calculatedAt = calculatedAt;
    }

    public long getGatewayId() {
        return sensorDataIndexInfo.getGatewayId();
    }

    public String getSensorId() {
        return sensorDataIndexInfo.getSensorId();
    }

    public String getDataTypeEnName() {
        return sensorDataIndexInfo.getTypeEnName();
    }
}
