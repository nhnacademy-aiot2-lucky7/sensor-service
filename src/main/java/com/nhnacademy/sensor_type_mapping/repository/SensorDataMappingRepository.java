package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataMappingRepository extends JpaRepository<SensorDataMapping, Long>, CustomSensorDataMappingRepository {

}
