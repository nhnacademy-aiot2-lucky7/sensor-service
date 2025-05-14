package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingFrontResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingSearchRequest;

import java.util.List;

public interface CustomSensorDataMappingRepository {

    /**
     *
     */
    long countByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName);

    /**
     *
     */
    boolean existsByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName);

    /**
     * SELECT
     * FROM
     * WHERE
     */
    SensorDataMapping findByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName);

    long countByConditions(SensorDataMappingSearchRequest request);

    boolean existsByConditions(SensorDataMappingSearchRequest request);

    List<SensorDataMappingInfoResponse> findByConditions(SensorDataMappingSearchRequest request);

    List<SensorDataMappingAiResponse> findAllAiResponsesByGatewayId(String gatewayId);

    /**
     * SELECT
     * FROM
     * WHERE
     */
    List<SensorDataMappingFrontResponse> findMappingInfoBySensorId(String sensorId);
}
