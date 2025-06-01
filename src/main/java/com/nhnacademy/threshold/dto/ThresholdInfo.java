package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class ThresholdInfo {

    @NotNull(message = "분석된 최저 임계치 값을 전달받지 못했습니다.")
    private final Double thresholdMin;

    @NotNull(message = "분석된 최고 임계치 값을 전달받지 못했습니다.")
    private final Double thresholdMax;

    @NotNull(message = "분석된 평균 임계치 값을 전달받지 못했습니다.")
    private final Double thresholdAvg;

    @JsonCreator
    public ThresholdInfo(
            @JsonProperty("min") Double thresholdMin,
            @JsonProperty("max") Double thresholdMax,
            @JsonProperty("avg") Double thresholdAvg
    ) {
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.thresholdAvg = thresholdAvg;
    }
}
