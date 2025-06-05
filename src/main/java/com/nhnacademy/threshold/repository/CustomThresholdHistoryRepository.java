package com.nhnacademy.threshold.repository;

import com.nhnacademy.threshold.dto.ThresholdBoundResponse;
import com.nhnacademy.threshold.dto.ThresholdDiffResponse;
import com.nhnacademy.threshold.dto.ThresholdInfoResponse;

import java.util.List;

public interface CustomThresholdHistoryRepository {

    List<ThresholdDiffResponse> findLatestThresholdSummariesByGatewayId(long gatewayId);

    List<ThresholdBoundResponse> findLatestThresholdBoundsBySensor(
            long gatewayId, String sensorId
    );

    ThresholdBoundResponse findLatestThresholdBoundsBySensorData(
            long gatewayId, String sensorId,
            String typeEnName
    );

    List<ThresholdInfoResponse> findLatestThresholdInfosBySensorDataWithLimit(
            long gatewayId, String sensorId,
            String typeEnName, int limit
    );

    List<ThresholdDiffResponse> findThresholdDiffsByCalculatedAtRange(long start, long end);
}
