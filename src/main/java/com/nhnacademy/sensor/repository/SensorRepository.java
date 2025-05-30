package com.nhnacademy.sensor.repository;

import com.nhnacademy.sensor.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long>, CustomSensorRepository {

    /**
     * SELECT *
     * FROM sensors
     * WHERE gateway_id = ? AND sensor_id = ?
     */
    Sensor findByGatewayIdAndSensorId(long gatewayId, String sensorId);

    Sensor getReferenceByGatewayIdAndSensorId(long gatewayId, String sensorId);

    /**
     * SELECT COUNT(sensor_no)
     * FROM sensors
     * WHERE gateway_id = ? AND sensor_id = ?
     */
    boolean existsByGatewayIdAndSensorId(long gatewayId, String sensorId);
}
