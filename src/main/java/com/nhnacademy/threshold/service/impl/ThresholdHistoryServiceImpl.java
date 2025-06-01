package com.nhnacademy.threshold.service.impl;

import com.nhnacademy.common.exception.http.extend.SensorDataMappingNotFoundException;
import com.nhnacademy.common.exception.http.extend.ThresholdHistoryNotFoundException;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.repository.SensorDataMappingRepository;
import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.repository.ThresholdHistoryRepository;
import com.nhnacademy.threshold.service.ThresholdHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThresholdHistoryServiceImpl implements ThresholdHistoryService {

    private final ThresholdHistoryRepository thresholdHistoryRepository;

    private final SensorDataMappingRepository sensorDataMappingRepository;

    public ThresholdHistoryServiceImpl(
            ThresholdHistoryRepository thresholdHistoryRepository,
            SensorDataMappingRepository sensorDataMappingRepository
    ) {
        this.thresholdHistoryRepository = thresholdHistoryRepository;
        this.sensorDataMappingRepository = sensorDataMappingRepository;
    }

    @Override
    public void registerRequest(ThresholdHistoryInfo request) {
        if (
                !sensorDataMappingRepository.existsByGatewayIdAndSensorIdAndDataTypeEnName(
                        request.getGatewayId(),
                        request.getSensorId(),
                        request.getDataTypeEnName()
                )
        ) {
            throw new SensorDataMappingNotFoundException(
                    request.getGatewayId(),
                    request.getSensorId(),
                    request.getDataTypeEnName()
            );
        }

        SensorDataMapping sensorDataMapping =
                sensorDataMappingRepository.findByGatewayIdAndSensorIdAndDataTypeEnName(
                        request.getGatewayId(),
                        request.getSensorId(),
                        request.getDataTypeEnName()
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
                request.getDiffInfo().getDiffMin(),
                request.getDiffInfo().getDiffMax(),
                request.getDiffInfo().getDiffAvg(),
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
