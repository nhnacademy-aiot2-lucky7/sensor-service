package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class ThresholdDiffResponse {

    private final long gatewayId;

    private final String sensorId;

    @JsonProperty("type_en_name")
    private final String dataTypeEnName;

    private final Double minDiff;

    private final Double maxDiff;

    private final Double avgDiff;

    private final Long calculatedAt;

    @QueryProjection
    public ThresholdDiffResponse(
            long gatewayId, String sensorId, String dataTypeEnName,
            Double minDiff, Double maxDiff, Double avgDiff,
            Long calculatedAt
    ) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.dataTypeEnName = dataTypeEnName;
        this.minDiff = minDiff;
        this.maxDiff = maxDiff;
        this.avgDiff = avgDiff;
        this.calculatedAt = calculatedAt;
    }
}
