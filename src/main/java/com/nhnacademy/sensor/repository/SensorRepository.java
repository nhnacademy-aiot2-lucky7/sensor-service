package com.nhnacademy.sensor.repository;

import com.nhnacademy.sensor.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer>, CustomSensorRepository {

}
