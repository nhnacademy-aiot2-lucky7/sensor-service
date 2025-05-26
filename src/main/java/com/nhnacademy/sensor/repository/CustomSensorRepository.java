package com.nhnacademy.sensor.repository;

import com.nhnacademy.sensor.dto.SensorIndexResponse;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorSearchRequest;

import java.util.List;
import java.util.Set;

public interface CustomSensorRepository {

    long countByConditions(SensorSearchRequest request);

    /**
     * {@link CustomSensorRepository#countByConditions(SensorSearchRequest)} > 0
     */
    boolean existsByConditions(SensorSearchRequest request);

    List<SensorInfoResponse> findByConditions(SensorSearchRequest request);

    /**
     * SELECT gateway_id, sensor_id
     * FROM sensors
     * <hr>
     * {@code Key}: <b>SensorDataHandlerResponse</b>
     *
     * @see SensorIndexResponse
     */
    Set<SensorIndexResponse> findAllSensorUniqueKeys();
}
