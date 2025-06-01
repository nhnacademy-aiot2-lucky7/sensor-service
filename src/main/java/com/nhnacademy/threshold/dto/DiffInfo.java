package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class DiffInfo {

    private final Double diffMin;

    private final Double diffMax;

    private final Double diffAvg;

    @JsonCreator
    public DiffInfo(
            @JsonProperty("min") Double diffMin,
            @JsonProperty("max") Double diffMax,
            @JsonProperty("avg") Double diffAvg
    ) {
        this.diffMin = diffMin;
        this.diffMax = diffMax;
        this.diffAvg = diffAvg;
    }
}
