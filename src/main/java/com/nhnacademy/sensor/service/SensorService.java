package com.nhnacademy.sensor.service;

import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorInfo;
import com.nhnacademy.sensor.dto.SensorIndexResponse;
import com.nhnacademy.sensor.dto.SensorInfoResponse;

import java.util.Set;

public interface SensorService {

    void registerRequest(SensorInfo request);

    Sensor registerSensor(SensorInfo request);

    Sensor getSensor(SensorInfo request);

    Sensor getReferenceSensor(SensorInfo request);

    void updateSensor(SensorInfo request);

    void removeSensor(SensorInfo request);

    boolean isExistsSensor(SensorInfo request);

    SensorInfoResponse getSensorInfoResponse(SensorInfo request);

    Set<SensorIndexResponse> getSensorIndexes();
}
