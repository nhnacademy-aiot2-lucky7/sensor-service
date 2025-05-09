package com.nhnacademy.sensor_type_mapping.dto;

import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import lombok.Getter;
import lombok.ToString;

/**
 * 불필요한 P.K 및 F.K 데이터를 제외한 모든 데이터를 담는 DTO 클래스입니다.
 */
@Getter
@ToString
public class SensorMappingInfo {

    private final String gatewayId;

    private final String sensorId;

    private final SensorStatus sensorStatus;

    private final String sensorLocation;

    private final String sensorSpot;

    private final String dataTypeEnName;

    private final String dataTypeKrName;

    public SensorMappingInfo(
            String gatewayId, String sensorId, SensorStatus sensorStatus,
            String sensorLocation, String sensorSpot,
            String dataTypeEnName, String dataTypeKrName
    ) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.sensorStatus = sensorStatus;
        this.sensorLocation = sensorLocation;
        this.sensorSpot = sensorSpot;
        this.dataTypeEnName = dataTypeEnName;
        this.dataTypeKrName = dataTypeKrName;
    }
}
