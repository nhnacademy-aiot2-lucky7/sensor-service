package com.nhnacademy.sensor.repository;

import com.nhnacademy.sensor.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer>, CustomSensorRepository {

    /**
     * SELECT *
     * FROM sensors
     * WHERE gateway_id = ? AND sensor_id = ?
     */
    Sensor findByGatewayIdAndSensorId(String gatewayId, String sensorId);

    Sensor getReferenceByGatewayIdAndSensorId(String gatewayId, String sensorId);

    /**
     * SELECT COUNT(sensor_no)
     * FROM sensors
     * WHERE gateway_id = ? AND sensor_id = ?
     */
    boolean existsByGatewayIdAndSensorId(String gatewayId, String sensorId);
}
