package com.nhnacademy.threshold.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class ThresholdBoundResponse {

    private final String typeEnName;

    private final Double minRangeMin;

    private final Double maxRangeMax;

    @QueryProjection
    public ThresholdBoundResponse(
            String typeEnName,
            Double minRangeMin,
            Double maxRangeMax
    ) {
        this.typeEnName = typeEnName;
        this.minRangeMin = minRangeMin;
        this.maxRangeMax = maxRangeMax;
    }
}
