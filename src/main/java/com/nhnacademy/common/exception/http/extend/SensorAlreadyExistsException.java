package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.ConflictException;

public class SensorAlreadyExistsException extends ConflictException {

    public SensorAlreadyExistsException(long gatewayId, String sensorId) {
        this(gatewayId, sensorId, null);
    }

    public SensorAlreadyExistsException(long gatewayId, String sensorId, Throwable cause) {
        super("sensor already exists: {%d:%s}".formatted(gatewayId, sensorId), cause);
    }
}
