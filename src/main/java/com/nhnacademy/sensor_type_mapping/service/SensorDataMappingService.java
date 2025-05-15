package com.nhnacademy.sensor_type_mapping.service;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMappingInfo;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;

import java.util.Set;

public interface SensorDataMappingService {

    void registerRequest(SensorDataMappingInfo request);

    SensorDataMapping getSensorDataMapping(SensorDataMappingInfo request);

    void updateSensorDataMapping(SensorDataMappingInfo request);

    void removeSensorDataMapping(SensorDataMappingInfo request);

    boolean isExistsSensorDataMapping(SensorDataMappingInfo request);

    SensorDataMappingInfoResponse getSensorDataMappingInfoResponse(SensorDataMappingInfo request);

    Set<SensorDataMappingIndexResponse> getSensorDataMappingIndexes();
}
