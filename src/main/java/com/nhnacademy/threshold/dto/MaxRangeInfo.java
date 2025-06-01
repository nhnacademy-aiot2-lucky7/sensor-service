package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public final class MaxRangeInfo {

    @NotNull(message = "최고 임계치의 최소값을 전달받지 못했습니다.")
    private final Double maxRangeMin;

    @NotNull(message = "최고 임계치의 최대값을 전달받지 못했습니다.")
    private final Double maxRangeMax;

    @JsonCreator
    public MaxRangeInfo(
            @JsonProperty("min") Double maxRangeMin,
            @JsonProperty("max") Double maxRangeMax
    ) {
        this.maxRangeMin = maxRangeMin;
        this.maxRangeMax = maxRangeMax;
    }
}
