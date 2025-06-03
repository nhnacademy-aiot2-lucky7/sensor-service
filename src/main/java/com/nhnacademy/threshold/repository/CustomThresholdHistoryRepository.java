package com.nhnacademy.threshold.repository;

import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryResponse;
import com.nhnacademy.threshold.dto.ThresholdInfoResponse;

import java.util.List;

public interface CustomThresholdHistoryRepository {

    List<RuleEngineResponse> findLatestThresholdSummariesByGatewayId(long gatewayId);

    List<ThresholdHistoryResponse> findLatestThresholdInfoBySensor(
            long gatewayId, String sensorId
    );

    ThresholdHistoryResponse findLatestThresholdInfoBySensorData(
            long gatewayId, String sensorId,
            String typeEnName
    );

    List<ThresholdInfoResponse> findLatestThresholdInfoBySensorDataWithLimit(
            long gatewayId, String sensorId,
            String typeEnName, int limit
    );
}
