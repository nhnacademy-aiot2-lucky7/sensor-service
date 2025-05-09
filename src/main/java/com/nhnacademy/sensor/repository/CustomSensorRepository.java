package com.nhnacademy.sensor.repository;

import com.nhnacademy.sensor.dto.SensorInfoResponse;

import java.util.Set;

public interface CustomSensorRepository {

    /**
     * SELECT COUNT(sensor_no)
     * FROM sensors
     * WHERE sensor_id = ?
     */
    long countBySensorId(String sensorId);

    /**
     * {@link CustomSensorRepository#countBySensorId(String sensorId)} > 0
     */
    boolean existsBySensorId(String sensorId);

    /**
     * SELECT *
     * FROM sensors
     * WHERE sensor_id = ?
     */
    SensorInfoResponse findBySensorId(String sensorId);

    /**
     * SELECT sensor_id
     * FROM sensors
     * <hr>
     * {@code Key}: sensor_id
     */
    Set<String> findDistinctSensorIds();
}
