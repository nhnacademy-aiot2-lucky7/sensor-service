package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.ConflictException;

public class SensorDataMappingAlreadyExistsException extends ConflictException {

    public SensorDataMappingAlreadyExistsException(
            String gatewayId, String sensorId,
            String dataTypeEnName
    ) {
        this(
                gatewayId, sensorId,
                dataTypeEnName, null
        );
    }

    public SensorDataMappingAlreadyExistsException(
            String gatewayId, String sensorId,
            String dataTypeEnName, Throwable cause
    ) {
        super(
                "sensorDataMapping already exists: {%s:%s:%s}"
                        .formatted(gatewayId, sensorId, dataTypeEnName),
                cause
        );
    }
}
