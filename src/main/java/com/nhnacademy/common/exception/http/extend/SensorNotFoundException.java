package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.NotFoundException;

public class SensorNotFoundException extends NotFoundException {

    public SensorNotFoundException(int sensorNo) {
        this(sensorNo, null);
    }

    public SensorNotFoundException(int sensorNo, Throwable cause) {
        super("sensor not found: %d".formatted(sensorNo), cause);
    }

    public SensorNotFoundException(String gatewayId, String sensorId) {
        this(gatewayId, sensorId, null);
    }

    public SensorNotFoundException(String gatewayId, String sensorId, Throwable cause) {
        super("sensor not found: {%s:%s}".formatted(gatewayId, sensorId), cause);
    }
}
