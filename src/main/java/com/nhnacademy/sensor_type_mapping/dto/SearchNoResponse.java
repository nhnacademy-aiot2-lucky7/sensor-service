package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class SearchNoResponse {

    @JsonProperty("sensor_data_no")
    private final long sensorDataNo;

    @QueryProjection
    public SearchNoResponse(long sensorDataNo) {
        this.sensorDataNo = sensorDataNo;
    }
}
