package com.nhnacademy.sensor_type_mapping.dto;

import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import lombok.Value;

@Value
public class SensorMappingFrontResponse {

    String gatewayId;

    String sensorId;

    SensorStatus sensorStatus;

    String sensorLocation;

    String sensorSpot;

    String dataTypeEnName;

    String dataTypeKrName;
}
