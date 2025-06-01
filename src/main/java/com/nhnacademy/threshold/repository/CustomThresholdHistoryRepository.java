package com.nhnacademy.threshold.repository;

import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.dto.ThresholdInfoResponse;

import java.util.List;

public interface CustomThresholdHistoryRepository {

    List<RuleEngineResponse> findLatestThresholdSummariesByGatewayId(long gatewayId);

    List<ThresholdInfoResponse> findLatestThresholdInfoBySensorDataAndLimit(
            long gatewayId, String sensorId,
            String typeEnName, int limit
    );
}
