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

    List<ThresholdInfoResponse> getThresholdsBySensor(
            long gatewayId, String sensorId
    );

    ThresholdInfoResponse getLatestThresholdInfoBySensorData(
            long gatewayId, String sensorId,
            String typeEnName
    );

    List<ThresholdInfoResponse> getLatestThresholdsBySensorDataWithLimit(
            long gatewayId, String sensorId,
            String typeEnName, int limit
    );
}
