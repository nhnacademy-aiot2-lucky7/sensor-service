package com.nhnacademy.common.exception;

public class SensorNotFoundException extends NotFoundException {

    public SensorNotFoundException(int sensorNo) {
        this(sensorNo, null);
    }

    public SensorNotFoundException(int sensorNo, Throwable cause) {
        super("sensor is not found: %d".formatted(sensorNo), cause);
    }

    public SensorNotFoundException(String sensorId) {
        this(sensorId, null);
    }

    public SensorNotFoundException(String sensorId, Throwable cause) {
        super("sensor is not found: %s".formatted(sensorId), cause);
    }
}
