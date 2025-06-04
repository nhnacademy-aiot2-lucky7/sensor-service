package com.nhnacademy.threshold.service.impl;

import com.nhnacademy.common.context.TimeZoneContext;
import com.nhnacademy.common.exception.http.extend.SensorDataMappingNotFoundException;
import com.nhnacademy.common.exception.http.extend.ThresholdHistoryNotFoundException;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.repository.SensorDataMappingRepository;
import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.ThresholdBoundResponse;
import com.nhnacademy.threshold.dto.ThresholdDiffResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.dto.ThresholdInfoResponse;
import com.nhnacademy.threshold.repository.ThresholdHistoryRepository;
import com.nhnacademy.threshold.service.ThresholdHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
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
    public List<ThresholdDiffResponse> getLatestThresholdSummariesByGatewayId(long gatewayId) {
        return thresholdHistoryRepository.findLatestThresholdSummariesByGatewayId(gatewayId);
    }

    @Override
    public List<ThresholdBoundResponse> getLatestThresholdBoundsBySensor(
            long gatewayId, String sensorId
    ) {
        return thresholdHistoryRepository.findLatestThresholdBoundsBySensor(
                gatewayId, sensorId
        );
    }

    @Override
    public ThresholdBoundResponse getLatestThresholdBoundsBySensorData(
            long gatewayId, String sensorId,
            String typeEnName
    ) {
        ThresholdBoundResponse response =
                thresholdHistoryRepository.findLatestThresholdBoundsBySensorData(
                        gatewayId, sensorId,
                        typeEnName
                );
        if (response == null) {
            throw new ThresholdHistoryNotFoundException();
        }
        return response;
    }

    @Override
    public List<ThresholdInfoResponse> getLatestThresholdsBySensorDataWithLimit(
            long gatewayId, String sensorId,
            String typeEnName, int limit
    ) {
        return thresholdHistoryRepository.findLatestThresholdInfosBySensorDataWithLimit(
                gatewayId, sensorId,
                typeEnName, limit
        );
    }

    @Override
    public List<ThresholdDiffResponse> getThresholdDiffsByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        ZoneId zoneId = TimeZoneContext.getZoneId();

        ZonedDateTime startOfDay = localDate
                .atStartOfDay(zoneId);
        long start = startOfDay.toInstant().toEpochMilli();

        ZonedDateTime endOfDay = startOfDay
                .plusDays(1L);       // 1일 추가
        long end = endOfDay.toInstant().toEpochMilli();

        return thresholdHistoryRepository.findThresholdDiffsByCalculatedAtRange(start, end);
    }
}
