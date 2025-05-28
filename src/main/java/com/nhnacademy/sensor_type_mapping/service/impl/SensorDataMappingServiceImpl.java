package com.nhnacademy.sensor_type_mapping.service.impl;

import com.nhnacademy.common.exception.http.BadRequestException;
import com.nhnacademy.common.exception.http.extend.SensorDataMappingAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.SensorDataMappingNotFoundException;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.service.SensorService;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoRequest;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfo;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;
import com.nhnacademy.sensor_type_mapping.repository.SensorDataMappingRepository;
import com.nhnacademy.sensor_type_mapping.service.SensorDataMappingService;
import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.service.DataTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public void registerRequest(SensorDataMappingInfo request) {
        if (isExistsSensorDataMapping(request)) {
            throw new SensorDataMappingAlreadyExistsException(
                    request.getGatewayId(),
                    request.getSensorId(),
                    request.getDataTypeEnName()
            );
        }
        registerSensorDataMapping(request);
    }

    private void registerSensorDataMapping(SensorDataMappingInfo request) {
        Sensor sensor = sensorService.isExistsSensor(request.getSensorInfo())
                ? sensorService.getReferenceSensor(request.getSensorInfo())
                : sensorService.registerSensor(request.getSensorInfo());

        /// TODO: 번역기 라이브러리 적용하기
        DataType dataType = dataTypeService.isExistsDataType(request.getDataTypeEnName())
                ? dataTypeService.getReferenceDataType(request.getDataTypeEnName())
                : dataTypeService.registerDataType(request.getDataTypeEnName(), request.getDataTypeEnName());

        SensorDataMapping sensorDataMapping =
                SensorDataMapping.ofNewSensorDataType(
                        sensor,
                        dataType,
                        SensorStatus.PENDING
                );
        sensorDataMappingRepository.save(sensorDataMapping);
    }

    @Override
    public SensorDataMapping getSensorDataMapping(SensorDataMappingInfo request) {
        SensorDataMapping sensorDataMapping =
                sensorDataMappingRepository.findByGatewayIdAndSensorIdAndDataTypeEnName(
                        request.getGatewayId(),
                        request.getSensorId(),
                        request.getDataTypeEnName()
                );
        if (sensorDataMapping == null) {
            throw new SensorDataMappingNotFoundException(
                    request.getGatewayId(),
                    request.getSensorId(),
                    request.getDataTypeEnName()
            );
        }
        return sensorDataMapping;
    }

    @Override
    public void updateSensorDataMapping(SensorDataMappingInfo request) {
        SensorDataMapping sensorDataMapping = getSensorDataMapping(request);
        sensorDataMapping.updateStatus(request.getSensorStatus());
        sensorDataMappingRepository.flush();
    }

    @Override
    public void removeSensorDataMapping(SensorDataMappingInfo request) {
        SensorDataMapping sensorDataMapping = getSensorDataMapping(request);
        sensorDataMappingRepository.delete(sensorDataMapping);
        sensorDataMappingRepository.flush();
    }

    @Override
    public boolean isExistsSensorDataMapping(SensorDataMappingInfo request) {
        return sensorDataMappingRepository.existsByGatewayIdAndSensorIdAndDataTypeEnName(
                request.getGatewayId(),
                request.getSensorId(),
                request.getDataTypeEnName()
        );
    }

    /// P.K 정보 데이터
    @Transactional(readOnly = true)
    @Override
    public SearchNoResponse getSearchNoResponse(SearchNoRequest request) {
        SearchNoResponse response = sensorDataMappingRepository
                .findNoResponseByGatewayIdAndSensorIdAndDataTypeEnName(
                        request.getGatewayId(),
                        request.getSensorId(),
                        request.getDataTypeEnName()
                );
        if (response == null) {
            throw new SensorDataMappingNotFoundException(
                    request.getGatewayId(),
                    request.getSensorId(),
                    request.getDataTypeEnName()
            );
        }
        return response;
    }

    /// 상세 정보 데이터
    @Transactional(readOnly = true)
    @Override
    public SensorDataMappingInfoResponse getSensorDataMappingInfoResponse(SensorDataMappingInfo request) {
        SensorDataMappingInfoResponse response = sensorDataMappingRepository
                .findInfoResponseByGatewayIdAndSensorIdAndDataTypeEnName(
                        request.getGatewayId(),
                        request.getSensorId(),
                        request.getDataTypeEnName()
                );
        if (response == null) {
            throw new SensorDataMappingNotFoundException(
                    request.getGatewayId(),
                    request.getSensorId(),
                    request.getDataTypeEnName()
            );
        }
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SensorDataMappingAiResponse> getAiResponse(String gatewayId) {
        return sensorDataMappingRepository.findAllAiResponsesByGatewayId(gatewayId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SensorDataMappingAiResponse> getStatuses(List<String> statuses) {
        List<SensorStatus> sensorStatuses = new ArrayList<>();
        try {
            for (String status : statuses) {
                sensorStatuses.add(
                        SensorStatus.valueOf(status.toUpperCase())
                );
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(
                    "사용 가능한 값: [%s]"
                            .formatted(SensorStatus.VALID_VALUES_STRING)
            );
        }
        return sensorDataMappingRepository.findAllAiResponsesBySensorStatuses(sensorStatuses);
    }

    /// 검색용 데이터
    @Transactional(readOnly = true)
    @Override
    public Set<SensorDataMappingIndexResponse> getIndexes() {
        return sensorDataMappingRepository.findAllSensorDataUniqueKeys();
    }
}
