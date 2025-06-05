package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class SensorDataMappingWebResponse {

    @JsonProperty("no")
    private final Long sensorNo;

    @JsonProperty("gateway_id")
    private final Long gatewayId;

    @JsonProperty("sensor_id")
    private final String sensorId;

    /*@JsonProperty("name")
    private final String sensorName;*/

    @JsonProperty("type_en_name")
    private final String typeEnName;

    @JsonProperty("location")
    private final String sensorLocation;

    @JsonProperty("spot")
    private final String sensorSpot;

    @QueryProjection
    public SensorDataMappingWebResponse(
            Long sensorNo, Long gatewayId, String sensorId, /*String sensorName,*/
            String typeEnName, String sensorLocation, String sensorSpot
    ) {
        this.sensorNo = sensorNo;
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        // this.sensorName = sensorName;
        this.typeEnName = typeEnName;
        this.sensorLocation = sensorLocation;
        this.sensorSpot = sensorSpot;
    }
}
