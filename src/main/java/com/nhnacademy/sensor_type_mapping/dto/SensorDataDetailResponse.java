package com.nhnacademy.sensor_type_mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public final class SensorDataDetailResponse {

    @JsonProperty("sensor_data_no")
    private final Long sensorNo;

    @JsonProperty("gateway_id")
    private final Long gatewayId;

    @JsonProperty("sensor_id")
    private final String sensorId;

    private final String sensorName;

    @JsonProperty("type_en_name")
    private final String typeEnName;

    @JsonProperty("type_unit")
    private final String typeUnit;

    @JsonProperty("location")
    private final String sensorLocation;

    @JsonProperty("spot")
    private final String sensorSpot;

    @JsonProperty("status")
    private final SensorStatus status;

    @QueryProjection
    public SensorDataDetailResponse(
            Long sensorNo, Long gatewayId, String sensorId, String sensorName,
            String typeEnName, String typeUnit, String sensorLocation, String sensorSpot, SensorStatus status
    ) {
        this.sensorNo = sensorNo;
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.typeEnName = typeEnName;
        this.typeUnit = typeUnit;
        this.sensorLocation = sensorLocation;
        this.sensorSpot = sensorSpot;
        this.status = status;
    }
}
