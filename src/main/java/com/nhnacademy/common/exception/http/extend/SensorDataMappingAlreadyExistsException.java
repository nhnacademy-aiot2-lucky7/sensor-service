package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.ConflictException;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingRegisterRequest;

public class SensorDataMappingAlreadyExistsException extends ConflictException {

    public SensorDataMappingAlreadyExistsException(
            SensorDataMappingRegisterRequest request
    ) {
        this(
                request.getGatewayId(), request.getSensorId(),
                request.getDataTypeEnName(), null
        );
    }

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
