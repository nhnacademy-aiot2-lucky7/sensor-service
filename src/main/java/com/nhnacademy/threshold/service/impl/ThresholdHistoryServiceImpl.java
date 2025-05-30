package com.nhnacademy.threshold.service.impl;

import com.nhnacademy.common.exception.http.extend.SensorDataMappingNotFoundException;
import com.nhnacademy.common.exception.http.extend.ThresholdHistoryNotFoundException;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.service.SensorDataMappingService;
import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.dto.RuleEngineResponse;
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

    @Override
    public void registerRequest(ThresholdHistoryInfo request) {
        if (!sensorDataMappingService.isExistsSensorDataMapping(request.getSensorDataMappingInfo())) {
            throw new SensorDataMappingNotFoundException(
                    request.getSensorDataMappingInfo().getGatewayId(),
                    request.getSensorDataMappingInfo().getSensorId(),
                    request.getSensorDataMappingInfo().getDataTypeEnName()
            );
        }

        SensorDataMapping sensorDataMapping =
                sensorDataMappingService.getSensorDataMapping(
                        request.getSensorDataMappingInfo()
                );

        ThresholdHistory thresholdHistory = new ThresholdHistory(
                request.getThresholdInfo().getThresholdMin(),
                request.getThresholdInfo().getThresholdMax(),
                request.getThresholdInfo().getThresholdAvg(),
                request.getMinRangeInfo().getMinRangeMin(),
                request.getMinRangeInfo().getMinRangeMax(),
                request.getMaxRangeInfo().getMaxRangeMin(),
                request.getMaxRangeInfo().getMaxRangeMax(),
                request.getAvgRangeInfo().getAvgRangeMin(),
                request.getAvgRangeInfo().getAvgRangeMax(),
                request.getDeltaInfo().getDeltaMin(),
                request.getDeltaInfo().getDeltaMax(),
                request.getDeltaInfo().getDeltaAvg(),
                request.getDataCount(),
                request.getCalculatedAt(),
                sensorDataMapping
        );
        thresholdHistoryRepository.save(thresholdHistory);
    }

    @Override
    public ThresholdHistory getThresholdHistoryByThresholdHistoryNo(long thresholdHistoryNo) {
        return thresholdHistoryRepository.findById(thresholdHistoryNo)
                .orElseThrow(() -> new ThresholdHistoryNotFoundException(thresholdHistoryNo));
    }

    @Override
    public List<RuleEngineResponse> getLatestThresholdSummariesByGatewayId(long gatewayId) {
        return thresholdHistoryRepository.findLatestThresholdSummariesByGatewayId(gatewayId);
    }
}
