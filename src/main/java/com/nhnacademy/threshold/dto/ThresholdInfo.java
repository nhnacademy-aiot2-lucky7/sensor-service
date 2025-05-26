package com.nhnacademy.threshold.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ThresholdInfo {

    @NotNull(message = "최저 임계치 값을 전달받지 못했습니다.")
    Double thresholdMin;

    @NotNull(message = "최고 임계치 값을 전달받지 못했습니다.")
    Double thresholdMax;

    @NotNull(message = "평균 임계치 값을 전달받지 못했습니다.")
    Double thresholdAvg;
}
