package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.ConflictException;

public class SensorDataMappingAlreadyExistsException extends ConflictException {

    public SensorDataMappingAlreadyExistsException(
            long gatewayId, String sensorId,
            String dataTypeEnName
    ) {
        this(
                gatewayId, sensorId,
                dataTypeEnName, null
        );
    }

    public SensorDataMappingAlreadyExistsException(
            long gatewayId, String sensorId,
            String dataTypeEnName, Throwable cause
    ) {
        super(
                "sensorDataMapping already exists: {%d:%s:%s}"
                        .formatted(gatewayId, sensorId, dataTypeEnName),
                cause
        );
    }
}
