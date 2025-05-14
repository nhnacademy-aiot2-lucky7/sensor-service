package com.nhnacademy.sensor.service;

import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorDataHandlerResponse;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.sensor.dto.SensorUpdateRequest;

import java.util.List;
import java.util.Set;

public interface SensorService {

    void registerRequest(SensorRegisterRequest request);

    Sensor registerSensor(String gatewayId, String sensorId, String sensorLocation, String sensorSpot);

    Sensor getSensor(String gatewayId, String sensorId);

    Sensor getReferenceSensor(String gatewayId, String sensorId);

    void updateSensor(SensorUpdateRequest request);

    void removeSensor(String gatewayId, String sensorId);

    boolean isExistsSensor(String gatewayId, String sensorId);

    SensorInfoResponse getSensorInfoResponse(String gatewayId, String sensorId);

    List<SensorInfoResponse> getSearchSensorInfoResponse(String gatewayId);

    Set<SensorDataHandlerResponse> getSensorUniqueKeys();
}
