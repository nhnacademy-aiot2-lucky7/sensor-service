package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.sensor_type_mapping.domain.SensorMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorMappingRepository extends JpaRepository<SensorMapping, Long>, CustomSensorMappingRepository {

}
