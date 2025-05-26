package com.nhnacademy.threshold.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class MinRangeInfo {

    @NotNull(message = "최저 임계치의 최소값을 전달받지 못했습니다.")
    Double minRangeMin;

    @NotNull(message = "최저 임계치의 최대값을 전달받지 못했습니다.")
    Double minRangeMax;
}
