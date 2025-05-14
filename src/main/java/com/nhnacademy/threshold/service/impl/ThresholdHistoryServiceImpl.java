package com.nhnacademy.threshold.service.impl;

import com.nhnacademy.common.exception.http.extend.ThresholdHistoryNotFoundException;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.service.SensorDataMappingService;
import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryRegisterRequest;
import com.nhnacademy.threshold.repository.ThresholdHistoryRepository;
import com.nhnacademy.threshold.service.ThresholdHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThresholdHistoryServiceImpl implements ThresholdHistoryService {

    private final SensorDataMappingService sensorDataMappingService;

    private final ThresholdHistoryRepository thresholdHistoryRepository;

    public ThresholdHistoryServiceImpl(
            SensorDataMappingService sensorDataMappingService,
            ThresholdHistoryRepository thresholdHistoryRepository
    ) {
        this.sensorDataMappingService = sensorDataMappingService;
        this.thresholdHistoryRepository = thresholdHistoryRepository;
    }

    /// TODO: 전용 예외처리 추가 예정...
    @Override
    public void registerRequest(ThresholdHistoryRegisterRequest request) {
        if (!sensorDataMappingService.isExistsSensorDataMapping(request.getGatewayId(), request.getSensorId(), request.getDataTypeEnName())) {
            throw new RuntimeException("존재하지 않는 센서");
        }
        SensorDataMapping sensorDataMapping =
                sensorDataMappingService.getSensorDataMapping(request.getGatewayId(), request.getSensorId(), request.getDataTypeEnName());

        ThresholdHistory thresholdHistory = new ThresholdHistory(
                request.getThresholdMin(), request.getThresholdMax(), request.getThresholdAvg(),
                request.getMinRangeMin(), request.getMinRangeMax(), request.getMaxRangeMin(),
                request.getMaxRangeMax(), request.getAvgRangeMin(), request.getAvgRangeMax(),
                request.getDeltaMin(), request.getDeltaMax(), request.getDeltaAvg(),
                request.getDataCount(), request.getCalculatedAt(), sensorDataMapping
        );
        thresholdHistoryRepository.save(thresholdHistory);
    }

    @Override
    public ThresholdHistory getThresholdHistoryByThresholdHistoryNo(long thresholdHistoryNo) {
        return thresholdHistoryRepository.findById(thresholdHistoryNo)
                .orElseThrow(() -> new ThresholdHistoryNotFoundException(thresholdHistoryNo));
    }

    @Override
    public List<RuleEngineResponse> getLatestThresholdSummariesByGatewayId(String gatewayId) {
        return thresholdHistoryRepository.findLatestThresholdSummariesByGatewayId(gatewayId);
    }
}
