package com.nhnacademy.sensor_type_mapping.service;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingRegisterRequest;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingSearchRequest;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingUpdateRequest;

import java.util.List;

public interface SensorDataMappingService {

    void registerRequest(SensorDataMappingRegisterRequest request);

    SensorDataMapping getSensorDataMapping(String gatewayId, String sensorId, String dataTypeEnName);

    void updateSensorDataMapping(SensorDataMappingUpdateRequest request);

    void removeSensorDataMapping(String gatewayId, String sensorId, String dataTypeEnName);

    boolean isExistsSensorDataMapping(String gatewayId, String sensorId, String dataTypeEnName);

    List<SensorDataMappingInfoResponse> getSearchSensorDataMappingInfoResponse(SensorDataMappingSearchRequest request);

    List<SensorDataMappingAiResponse> getSensorDataMappingAiResponse(String gatewayId);
}
