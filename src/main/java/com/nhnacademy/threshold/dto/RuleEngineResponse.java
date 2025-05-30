package com.nhnacademy.threshold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class RuleEngineResponse {

    @JsonProperty("gateway_id")
    long gatewayId;

    @JsonProperty("sensor_id")
    String sensorId;

    @JsonProperty("type_en_name")
    String dataTypeEnName;

    @JsonProperty("threshold_min")
    Double thresholdMin;

    @JsonProperty("threshold_max")
    Double thresholdMax;

    @JsonProperty("threshold_ave")
    Double thresholdAve;

    @JsonProperty("avg_range_min")
    Double avgRangeMin;

    @JsonProperty("avg_range_max")
    Double avgRangeMax;
}
