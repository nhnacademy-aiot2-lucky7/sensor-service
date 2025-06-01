package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public final class AvgRangeInfo {

    @NotNull(message = "평균 임계치의 최소값을 전달받지 못했습니다.")
    private final Double avgRangeMin;

    @NotNull(message = "평균 임계치의 최대값을 전달받지 못했습니다.")
    private final Double avgRangeMax;

    @JsonCreator
    public AvgRangeInfo(
            @JsonProperty("min") Double avgRangeMin,
            @JsonProperty("max") Double avgRangeMax
    ) {
        this.avgRangeMin = avgRangeMin;
        this.avgRangeMax = avgRangeMax;
    }
}
