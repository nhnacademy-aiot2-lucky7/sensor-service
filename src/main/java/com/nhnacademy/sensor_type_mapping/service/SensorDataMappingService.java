package com.nhnacademy.sensor_type_mapping.service;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoRequest;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfo;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;

import java.util.List;
import java.util.Set;

public interface SensorDataMappingService {

    void registerRequest(SensorDataMappingInfo request);

    SensorDataMapping getSensorDataMapping(SensorDataMappingInfo request);

    void updateSensorDataMapping(SensorDataMappingInfo request);

    void removeSensorDataMapping(SensorDataMappingInfo request);

    boolean isExistsSensorDataMapping(SensorDataMappingInfo request);

    SearchNoResponse getSearchNoResponse(SearchNoRequest request);

    SensorDataMappingInfoResponse getSensorDataMappingInfoResponse(SensorDataMappingInfo request);

    List<SensorDataMappingAiResponse> getList(String gatewayId);

    Set<SensorDataMappingIndexResponse> getSensorDataMappingIndexes();
}
