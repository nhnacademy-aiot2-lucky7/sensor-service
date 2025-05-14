package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.ConflictException;
import com.nhnacademy.sensor.dto.SensorRegisterRequest;

public class SensorAlreadyExistsException extends ConflictException {

    public SensorAlreadyExistsException(int sensorNo) {
        this(sensorNo, null);
    }

    public SensorAlreadyExistsException(int sensorNo, Throwable cause) {
        super("sensor already exists: %d".formatted(sensorNo), cause);
    }

    public SensorAlreadyExistsException(SensorRegisterRequest request) {
        this(request.getGatewayId(), request.getSensorId(), null);
    }

    public SensorAlreadyExistsException(String gatewayId, String sensorId) {
        this(gatewayId, sensorId, null);
    }

    public SensorAlreadyExistsException(String gatewayId, String sensorId, Throwable cause) {
        super("sensor already exists: {%s:%s}".formatted(gatewayId, sensorId), cause);
    }
}
