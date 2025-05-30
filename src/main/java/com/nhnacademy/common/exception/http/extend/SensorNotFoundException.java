package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.NotFoundException;

public class SensorNotFoundException extends NotFoundException {

    public SensorNotFoundException(long gatewayId, String sensorId) {
        this(gatewayId, sensorId, null);
    }

    public SensorNotFoundException(long gatewayId, String sensorId, Throwable cause) {
        super("sensor not found: {%d:%s}".formatted(gatewayId, sensorId), cause);
    }
}
