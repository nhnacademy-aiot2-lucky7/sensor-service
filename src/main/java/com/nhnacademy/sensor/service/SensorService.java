package com.nhnacademy.sensor.service;

import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorIndexResponse;
import com.nhnacademy.sensor.dto.SensorInfo;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorNameUpdateRequest;
import com.nhnacademy.sensor.dto.SensorPositionUpdateRequest;

import java.util.Set;

public interface SensorService {

    void registerRequest(SensorInfo request);

    Sensor registerSensor(SensorInfo request);

    Sensor getSensor(SensorInfo request);

    Sensor getReferenceSensor(SensorInfo request);

    void updateSensorName(SensorNameUpdateRequest request);

    void updateSensorPosition(SensorPositionUpdateRequest request);

    void removeSensor(SensorInfo request);

    boolean isExistsSensor(SensorInfo request);

    SensorInfoResponse getSensorInfoResponse(SensorInfo request);

    Set<SensorIndexResponse> getSensorIndexes();
}
