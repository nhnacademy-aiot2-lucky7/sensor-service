package com.nhnacademy.threshold.service;

import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryRegisterRequest;

import java.util.List;

public interface ThresholdHistoryService {

    void registerRequest(ThresholdHistoryRegisterRequest request);

    ThresholdHistory getThresholdHistoryByThresholdHistoryNo(long thresholdHistoryNo);

    List<RuleEngineResponse> getLatestThresholdSummariesByGatewayId(String gatewayId);
}
