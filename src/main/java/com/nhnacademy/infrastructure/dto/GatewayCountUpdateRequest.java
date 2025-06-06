package com.nhnacademy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public final class GatewayCountUpdateRequest {

    @JsonProperty("gateway_id")
    private final Long gatewayId;

    @JsonProperty("sensor_count")
    private final Integer sensorCount;

    public GatewayCountUpdateRequest(Long gatewayId, Integer sensorCount) {
        this.gatewayId = gatewayId;
        this.sensorCount = sensorCount;
    }
}
