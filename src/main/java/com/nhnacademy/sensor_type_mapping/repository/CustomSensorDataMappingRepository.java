package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingSearchRequest;

import java.util.List;
import java.util.Set;

public interface CustomSensorDataMappingRepository {

    /**
     * SELECT COUNT(sensor_data_no)
     * FROM sensor_data_mappings
     * INNER JOIN sensors ON sensor_data_mappings.sensor_no = sensors.sensor_no
     * WHERE gateway_id = ? AND sensor_id = ? AND data_type_en_name = ?
     */
    long countByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName);

    /**
     * {@link CustomSensorDataMappingRepository#countByGatewayIdAndSensorIdAndDataTypeEnName(String, String, String)} > 0
     */
    boolean existsByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName);

    /**
     * SELECT SensorDataMapping
     * FROM sensor_data_mappings
     * INNER JOIN sensors ON sensor_data_mappings.sensor_no = sensors.sensor_no
     * WHERE gateway_id = ? AND sensor_id = ? AND data_type_en_name = ?
     */
    SensorDataMapping findByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName);

    long countByConditions(SensorDataMappingSearchRequest request);

    boolean existsByConditions(SensorDataMappingSearchRequest request);

    List<SensorDataMappingInfoResponse> findByConditions(SensorDataMappingSearchRequest request);

    SensorDataMappingInfoResponse findSensorDataMappingInfoResponseByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName);

    List<SensorDataMappingAiResponse> findAllAiResponsesByGatewayId(String gatewayId);

    Set<SensorDataMappingIndexResponse> findAllSensorDataUniqueKeys();
}
