package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public final class MinRangeInfo {

    @NotNull(message = "최저 임계치의 최소값을 전달받지 못했습니다.")
    private final Double minRangeMin;

    @NotNull(message = "최저 임계치의 최대값을 전달받지 못했습니다.")
    private final Double minRangeMax;

    @JsonCreator
    public MinRangeInfo(
            @JsonProperty("min") Double minRangeMin,
            @JsonProperty("max") Double minRangeMax
    ) {
        this.minRangeMin = minRangeMin;
        this.minRangeMax = minRangeMax;
    }
}
