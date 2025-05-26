package com.nhnacademy.threshold.repository;

import com.nhnacademy.threshold.dto.RuleEngineResponse;

import java.util.List;

public interface CustomThresholdHistoryRepository {

    List<RuleEngineResponse> findLatestThresholdSummariesByGatewayId(String gatewayId);
}
