package com.nhnacademy.sensor.dto;

import lombok.Value;

@Value
public class SensorInfoResponse {

    String gatewayId;

    String sensorId;

    String sensorLocation;

    String sensorSpot;
}
