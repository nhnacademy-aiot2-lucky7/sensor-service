package com.nhnacademy.threshold.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class MaxRangeInfo {

    @NotNull(message = "최고 임계치의 최소값을 전달받지 못했습니다.")
    Double maxRangeMin;

    @NotNull(message = "최고 임계치의 최대값을 전달받지 못했습니다.")
    Double maxRangeMax;
}
