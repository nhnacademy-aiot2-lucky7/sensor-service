package com.nhnacademy.threshold.service;

import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.ThresholdDiffResponse;
import com.nhnacademy.threshold.dto.ThresholdBoundResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.dto.ThresholdInfoResponse;

import java.util.List;

public interface ThresholdHistoryService {

    void registerRequest(ThresholdHistoryInfo request);

    ThresholdHistory getThresholdHistoryByThresholdHistoryNo(long thresholdHistoryNo);

    List<ThresholdDiffResponse> getLatestThresholdSummariesByGatewayId(long gatewayId);

    List<ThresholdBoundResponse> getLatestThresholdBoundsBySensor(
            long gatewayId, String sensorId
    );

    ThresholdBoundResponse getLatestThresholdBoundsBySensorData(
            long gatewayId, String sensorId,
            String typeEnName
    );

    List<ThresholdInfoResponse> getLatestThresholdsBySensorDataWithLimit(
            long gatewayId, String sensorId,
            String typeEnName, int limit
    );

    List<ThresholdDiffResponse> getThresholdDiffsByDate(String date);
}
