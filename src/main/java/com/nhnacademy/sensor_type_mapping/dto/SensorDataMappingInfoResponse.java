package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import lombok.Value;

@Value
public class SensorDataMappingInfoResponse {

    @JsonProperty("gateway_id")
    String gatewayId;

    @JsonProperty("sensor_id")
    String sensorId;

    @JsonProperty("sensor_location")
    String sensorLocation;

    @JsonProperty("sensor_spot")
    String sensorSpot;

    @JsonProperty("sensor_status")
    SensorStatus sensorStatus;

    @JsonProperty("type_en_name")
    String dataTypeEnName;
}
