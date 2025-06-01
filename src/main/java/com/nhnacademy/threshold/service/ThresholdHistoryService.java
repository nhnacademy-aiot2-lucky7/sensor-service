package com.nhnacademy.threshold.service;

import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.dto.ThresholdInfoResponse;

import java.util.List;

public interface ThresholdHistoryService {

    void registerRequest(ThresholdHistoryInfo request);

    ThresholdHistory getThresholdHistoryByThresholdHistoryNo(long thresholdHistoryNo);

    List<RuleEngineResponse> getLatestThresholdSummariesByGatewayId(long gatewayId);

    List<ThresholdInfoResponse> getLatestThresholdInfoBySensorDataAndLimit(
            long gatewayId, String sensorId,
            String typeEnName, int limit
    );
}
