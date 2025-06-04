package com.nhnacademy.sensor_type_mapping.service;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoRequest;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataIndexInfo;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfo;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingWebResponse;

import java.util.List;
import java.util.Set;

public interface SensorDataMappingService {

    void registerRequest(SensorDataMappingInfo request);

    SensorDataMapping getSensorDataMapping(SensorDataMappingInfo request);

    /**
     * @deprecated DTO 클래스 구조를 수정할 예정...
     */
    void updateSensorDataMapping(SensorDataMappingInfo request);

    void updateSensorStatus(SensorDataIndexInfo request);

    void removeSensorDataMapping(SensorDataMappingInfo request);

    boolean isExistsSensorDataMapping(SensorDataMappingInfo request);

    SearchNoResponse getSearchNoResponse(SearchNoRequest request);

    SensorDataMappingResponse getSensorDataMappingInfoResponse(SensorDataMappingInfo request);

    List<SensorDataMappingWebResponse> getSensorDataMappings(long gatewayId);

    List<SensorDataMappingAiResponse> getAiResponse(long gatewayId);

    List<SensorDataMappingAiResponse> getStatuses(List<String> statuses);

    Set<SensorDataMappingIndexResponse> getIndexes();
}
