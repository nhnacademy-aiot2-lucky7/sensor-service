package com.nhnacademy.threshold.controller;

import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.service.ThresholdHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/threshold-histories")
public class ThresholdHistoryController {

    private final ThresholdHistoryService thresholdHistoryService;

    public ThresholdHistoryController(ThresholdHistoryService thresholdHistoryService) {
        this.thresholdHistoryService = thresholdHistoryService;
    }

    @GetMapping("/{gateway_id}")
    public ResponseEntity<List<RuleEngineResponse>> getGatewayInSensors(
            @PathVariable("gateway_id") Long gatewayId
    ) {
        return ResponseEntity
                .ok(thresholdHistoryService.getLatestThresholdSummariesByGatewayId(gatewayId));
    }

    /**
     * 임계치 분석 전, 분석 후, 총 2번 보내게 된다.
     * 2시에 모든 임계치 계산 후, 저장
     * AI 쪽에서 요청을 보내고, 대신 분석 완료된 상태만
     * 1시 30분
     */
    /*public ResponseEntity<Void> getList() {

    }*/

    @PostMapping
    public ResponseEntity<Void> registerThresholdHistory(
            @Validated @RequestBody ThresholdHistoryInfo request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
