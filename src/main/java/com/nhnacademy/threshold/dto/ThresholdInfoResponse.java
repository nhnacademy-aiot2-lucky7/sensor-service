package com.nhnacademy.threshold.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class ThresholdInfoResponse {

    private final Double thresholdMin;

    private final Double thresholdMax;

    private final Double thresholdAvg;

    @QueryProjection
    public ThresholdInfoResponse(Double thresholdMin, Double thresholdMax, Double thresholdAvg) {
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.thresholdAvg = thresholdAvg;
    }
}
