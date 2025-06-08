package com.nhnacademy.sensor_type_mapping.service.impl;

import com.nhnacademy.common.exception.http.extend.SensorDataMappingAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.SensorDataMappingNotFoundException;
import com.nhnacademy.infrastructure.adapter.GatewayServiceAdapter;
import com.nhnacademy.infrastructure.dto.GatewayCountUpdateRequest;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.service.SensorService;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoRequest;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataDetailResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataIndexInfo;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfo;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingResponse;
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
@Transactional
public class SensorDataMappingServiceImpl implements SensorDataMappingService {

    private final SensorService sensorService;

    private final DataTypeService dataTypeService;

    private final SensorDataMappingRepository sensorDataMappingRepository;

    private final GatewayServiceAdapter gatewayServiceAdapter;

    public SensorDataMappingServiceImpl(
            SensorService sensorService, DataTypeService dataTypeService,
            SensorDataMappingRepository sensorDataMappingRepository,
            GatewayServiceAdapter gatewayServiceAdapter
    ) {
        this.sensorService = sensorService;
        this.dataTypeService = dataTypeService;
        this.sensorDataMappingRepository = sensorDataMappingRepository;
        this.gatewayServiceAdapter = gatewayServiceAdapter;
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

        long gatewayId = sensor.getGatewayId();
        GatewayCountUpdateRequest updateRequest = new GatewayCountUpdateRequest(
                gatewayId,
                sensorDataMappingRepository.countByGatewayId(gatewayId)
        );
        gatewayServiceAdapter.updateGatewaySensorCount(updateRequest);
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

    /**
     * @deprecated
     */
    @Override
    public void updateSensorDataMapping(SensorDataMappingInfo request) {
        SensorDataMapping sensorDataMapping = getSensorDataMapping(request);
        sensorDataMapping.updateStatus(request.getSensorStatus());
        sensorDataMappingRepository.flush();
    }

    /// TODO: Service 영역을 수정할 예정...
    @Override
    public void updateSensorStatus(SensorDataIndexInfo request) {
        SensorDataMapping sensorDataMapping =
                sensorDataMappingRepository.findByGatewayIdAndSensorIdAndDataTypeEnName(
                        request.getGatewayId(),
                        request.getSensorId(),
                        request.getTypeEnName()
                );
        if (sensorDataMapping == null) {
            throw new SensorDataMappingNotFoundException(
                    request.getGatewayId(),
                    request.getSensorId(),
                    request.getTypeEnName()
            );
        }
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
    public SensorDataMappingResponse getSensorDataMappingInfoResponse(SensorDataMappingInfo request) {
        SensorDataMappingResponse response = sensorDataMappingRepository
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
    public List<SensorDataDetailResponse> getSensorDataMappings(long gatewayId) {
        return sensorDataMappingRepository.findAllWebResponseByGatewayId(gatewayId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SensorDataMappingAiResponse> getAiResponse(long gatewayId) {
        return sensorDataMappingRepository.findAllAiResponsesByGatewayId(gatewayId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SensorDataMappingAiResponse> getStatuses(List<String> statuses) {
        List<SensorStatus> sensorStatuses = new ArrayList<>();
        for (String status : statuses) {
            sensorStatuses.add(
                    SensorStatus.from(status)
            );
        }
        return sensorDataMappingRepository.findAllAiResponsesBySensorStatuses(sensorStatuses);
    }

    @Override
    public List<SensorDataMappingAiResponse> getStatusesByGatewayId(Long gatewayId, List<String> statuses) {
        List<SensorStatus> sensorStatuses = new ArrayList<>();
        for (String status : statuses) {
            sensorStatuses.add(
                    SensorStatus.from(status)
            );
        }
        return sensorDataMappingRepository.findAllAiResponsesBySensorStatusesAndGatewayID(gatewayId, sensorStatuses);
    }

    /// 검색용 데이터
    @Transactional(readOnly = true)
    @Override
    public Set<SensorDataMappingIndexResponse> getIndexes() {
        return sensorDataMappingRepository.findAllSensorDataUniqueKeys();
    }
}
