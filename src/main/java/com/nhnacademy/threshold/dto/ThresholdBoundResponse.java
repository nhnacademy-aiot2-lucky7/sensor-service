package com.nhnacademy.threshold.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class ThresholdBoundResponse {

    private final String typeEnName;

    private final Double minRangeMin;

    private final Double minRangeMax;

    private final Double maxRangeMin;

    private final Double maxRangeMax;

    @QueryProjection
    public ThresholdBoundResponse(
            String typeEnName,
            Double minRangeMin, Double minRangeMax,
            Double maxRangeMin, Double maxRangeMax
    ) {
        this.typeEnName = typeEnName;
        this.minRangeMin = minRangeMin;
        this.minRangeMax = minRangeMax;
        this.maxRangeMin = maxRangeMin;
        this.maxRangeMax = maxRangeMax;
    }
}
