package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.sensor_type_mapping.domain.SensorMapping;
import com.nhnacademy.sensor_type_mapping.dto.SensorMappingInfo;

import java.util.List;

public interface CustomSensorMappingRepository {

    /**
     * SELECT
     * FROM
     * WHERE
     */
    SensorMapping findByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName);

    /**
     * SELECT
     * FROM
     * WHERE
     */
    List<SensorMappingInfo> findMappingInfoBySensorId(String sensorId);
}
