package com.nhnacademy.threshold.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class ThresholdInfoResponse {

    private final String typeEnName;

    private final Double thresholdMin;

    private final Double thresholdMax;

    private final Double thresholdAvg;

    @QueryProjection
    public ThresholdInfoResponse(
            String typeEnName,
            Double thresholdMin,
            Double thresholdMax,
            Double thresholdAvg
    ) {
        this.typeEnName = typeEnName;
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.thresholdAvg = thresholdAvg;
    }
}
