package com.nhnacademy.sensor_type_mapping.service.impl;

import com.nhnacademy.common.exception.http.extend.SensorDataMappingAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.SensorDataMappingNotFoundException;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.service.SensorService;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingRegisterRequest;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingSearchRequest;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingUpdateRequest;
import com.nhnacademy.sensor_type_mapping.repository.SensorDataMappingRepository;
import com.nhnacademy.sensor_type_mapping.service.SensorDataMappingService;
import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.service.DataTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorDataMappingServiceImpl implements SensorDataMappingService {

    private final SensorService sensorService;

    private final DataTypeService dataTypeService;

    private final SensorDataMappingRepository sensorDataMappingRepository;

    public SensorDataMappingServiceImpl(
            SensorService sensorService, DataTypeService dataTypeService,
            SensorDataMappingRepository sensorDataMappingRepository
    ) {
        this.sensorService = sensorService;
        this.dataTypeService = dataTypeService;
        this.sensorDataMappingRepository = sensorDataMappingRepository;
    }

    @Override
    public void registerRequest(SensorDataMappingRegisterRequest request) {
        if (isExistsSensorDataMapping(request.getGatewayId(), request.getSensorId(), request.getDataTypeEnName())) {
            throw new SensorDataMappingAlreadyExistsException(request);
        }
        registerSensorDataMapping(
                request.getGatewayId(), request.getSensorId(),
                request.getSensorLocation(), request.getSensorSpot(),
                request.getDataTypeEnName()
        );
    }

    private void registerSensorDataMapping(
            String gatewayId, String sensorId, String sensorLocation,
            String sensorSpot, String dataTypeEnName
    ) {
        Sensor sensor =
                sensorService.isExistsSensor(gatewayId, sensorId) ?
                        sensorService.getReferenceSensor(
                                gatewayId, sensorId
                        ) :
                        sensorService.registerSensor(
                                gatewayId, sensorId,
                                sensorLocation, sensorSpot
                        );

        DataType dataType =
                dataTypeService.isExistsDataType(dataTypeEnName) ?
                        dataTypeService.getReferenceDataType(
                                dataTypeEnName
                        ) :
                        dataTypeService.registerDataType(
                                dataTypeEnName,
                                dataTypeEnName  /// TODO: 번역기 라이브러리 적용하기
                        );

        SensorDataMapping sensorDataMapping =
                SensorDataMapping.ofNewSensorDataType(
                        sensor,
                        dataType,
                        SensorStatus.PENDING
                );
        sensorDataMappingRepository.save(sensorDataMapping);
    }

    @Override
    public SensorDataMapping getSensorDataMapping(String gatewayId, String sensorId, String dataTypeEnName) {
        SensorDataMapping sensorDataMapping = sensorDataMappingRepository.findByGatewayIdAndSensorIdAndDataTypeEnName(gatewayId, sensorId, dataTypeEnName);
        if (sensorDataMapping == null) {
            throw new SensorDataMappingNotFoundException(gatewayId, sensorId, dataTypeEnName);
        }
        return sensorDataMapping;
    }

    @Override
    public void updateSensorDataMapping(SensorDataMappingUpdateRequest request) {
        SensorDataMapping sensorDataMapping = getSensorDataMapping(
                request.getGatewayId(),
                request.getSensorId(),
                request.getDataTypeEnName()
        );
        sensorDataMapping.updateStatus(request.getSensorStatus());
        sensorDataMappingRepository.flush();
    }

    @Override
    public void removeSensorDataMapping(String gatewayId, String sensorId, String dataTypeEnName) {
        sensorDataMappingRepository.delete(getSensorDataMapping(gatewayId, sensorId, dataTypeEnName));
    }

    @Override
    public boolean isExistsSensorDataMapping(String gatewayId, String sensorId, String dataTypeEnName) {
        return sensorDataMappingRepository.existsByGatewayIdAndSensorIdAndDataTypeEnName(gatewayId, sensorId, dataTypeEnName);
    }

    @Override
    public List<SensorDataMappingInfoResponse> getSearchSensorDataMappingInfoResponse(
            SensorDataMappingSearchRequest request
    ) {
        return sensorDataMappingRepository.findByConditions(request);
    }

    @Override
    public List<SensorDataMappingAiResponse> getSensorDataMappingAiResponse(String gatewayId) {
        return sensorDataMappingRepository.findAllAiResponsesByGatewayId(gatewayId);
    }
}
