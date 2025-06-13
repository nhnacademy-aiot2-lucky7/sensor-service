package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.sensor_type_mapping.dto.*;

import java.util.List;
import java.util.Set;

public interface CustomSensorDataMappingRepository {

    /**
     * SELECT COUNT(sensor_data_no)
     * FROM sensor_data_mappings
     * INNER JOIN sensors ON sensor_data_mappings.sensor_no = sensors.sensor_no
     * WHERE gateway_id = ? AND sensor_id = ? AND data_type_en_name = ?
     */
    long countByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName);

    /**
     * {@link CustomSensorDataMappingRepository#countByGatewayIdAndSensorIdAndDataTypeEnName(long, String, String)} > 0
     */
    boolean existsByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName);

    /**
     * SELECT SensorDataMapping
     * FROM sensor_data_mappings
     * INNER JOIN sensors ON sensor_data_mappings.sensor_no = sensors.sensor_no
     * WHERE gateway_id = ? AND sensor_id = ? AND data_type_en_name = ?
     */
    SensorDataMapping findByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName);

    long countByConditions(SensorDataMappingSearchRequest request);

    boolean existsByConditions(SensorDataMappingSearchRequest request);

    List<SensorDataMappingResponse> findByConditions(SensorDataMappingSearchRequest request);

    SearchNoResponse findNoResponseByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName);

    SensorDataMappingResponse findInfoResponseByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName);

    List<SensorDataDetailResponse> findAllWebResponseByGatewayId(long gatewayId);

    List<SensorDataMappingAiResponse> findAllAiResponsesByGatewayId(long gatewayId);

    List<SensorDataMappingAiResponse> findAllAiResponsesBySensorStatuses(List<SensorStatus> sensorStatuses);

    List<SensorDataDetailResponse> findAllAiResponsesBySensorStatusesAndGatewayId(Long gatewayId, List<SensorStatus> sensorStatuses);

    Set<SensorDataMappingIndexResponse> findAllSensorDataUniqueKeys();

    int countByGatewayId(long gatewayId);
}
