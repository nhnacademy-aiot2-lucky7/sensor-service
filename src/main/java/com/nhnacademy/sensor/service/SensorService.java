package com.nhnacademy.sensor.service;

import com.nhnacademy.sensor.domain.Sensor;

import java.util.Set;

public interface SensorService {

    Sensor getSensorBySensorNo(int sensorNo);

    Set<String> getSensorIds();
}
