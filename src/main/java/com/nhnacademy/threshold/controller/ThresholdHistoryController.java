package com.nhnacademy.threshold.controller;

import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.dto.RuleEngineResponse;
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
            @PathVariable("gateway_id") String gatewayId
    ) {
        return ResponseEntity
                .ok(thresholdHistoryService.getLatestThresholdSummariesByGatewayId(gatewayId));
    }

    @PostMapping
    public ResponseEntity<Void> registerThresholdHistory(
            @Validated @RequestBody ThresholdHistoryInfo request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
