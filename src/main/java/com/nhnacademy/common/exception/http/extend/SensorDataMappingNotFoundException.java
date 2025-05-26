package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.NotFoundException;

public class SensorDataMappingNotFoundException extends NotFoundException {


    public SensorDataMappingNotFoundException(
            String gatewayId, String sensorId,
            String dataTypeEnName
    ) {
        this(
                gatewayId, sensorId,
                dataTypeEnName, null
        );
    }

    public SensorDataMappingNotFoundException(
            String gatewayId, String sensorId,
            String dataTypeEnName, Throwable cause
    ) {
        super(
                "sensorDataMapping not found: {%s:%s:%s}"
                        .formatted(gatewayId, sensorId, dataTypeEnName),
                cause
        );
    }
}
