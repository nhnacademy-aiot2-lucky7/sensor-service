package com.nhnacademy.threshold.service;

import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.dto.RuleEngineResponse;

import java.util.List;

public interface ThresholdHistoryService {

    void registerRequest(ThresholdHistoryInfo request);

    ThresholdHistory getThresholdHistoryByThresholdHistoryNo(long thresholdHistoryNo);

    List<RuleEngineResponse> getLatestThresholdSummariesByGatewayId(String gatewayId);
}
