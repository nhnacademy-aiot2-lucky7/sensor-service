package com.nhnacademy.threshold.controller;

import com.nhnacademy.threshold.dto.ThresholdDiffResponse;
import com.nhnacademy.threshold.dto.ThresholdBoundResponse;
import com.nhnacademy.threshold.dto.ThresholdHistoryInfo;
import com.nhnacademy.threshold.dto.ThresholdInfoResponse;
import com.nhnacademy.threshold.service.ThresholdHistoryService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/threshold-histories")
public class ThresholdHistoryController {

    private final ThresholdHistoryService thresholdHistoryService;

    public ThresholdHistoryController(ThresholdHistoryService thresholdHistoryService) {
        this.thresholdHistoryService = thresholdHistoryService;
    }

    @GetMapping("/{gateway_id}")
    public ResponseEntity<List<ThresholdDiffResponse>> getGatewayInSensors(
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
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ThresholdDiffResponse>> getThresholdsByDate(
            @PathVariable String date
    ) {
        return ResponseEntity
                .ok(thresholdHistoryService.getThresholdDiffsByDate(date));
    }

    @GetMapping("/gateway-id/{gateway-id}/sensor-id/{sensor-id}")
    public ResponseEntity<List<ThresholdBoundResponse>> getThresholdsBySensor(
            @PathVariable("gateway-id") Long gatewayId,
            @PathVariable("sensor-id") String sensorId
    ) {
        return ResponseEntity
                .ok(
                        thresholdHistoryService.getLatestThresholdBoundsBySensor(
                                gatewayId, sensorId
                        )
                );
    }

    @GetMapping("/gateway-id/{gateway-id}/sensor-id/{sensor-id}/type-en-name/{type-en-name}")
    public ResponseEntity<ThresholdBoundResponse> getThresholdsBySensorData(
            @PathVariable("gateway-id") Long gatewayId,
            @PathVariable("sensor-id") String sensorId,
            @PathVariable("type-en-name") String typeEnName
    ) {
        return ResponseEntity
                .ok(
                        thresholdHistoryService.getLatestThresholdBoundsBySensorData(
                                gatewayId, sensorId,
                                typeEnName
                        )
                );
    }

    @GetMapping("/gateway-id/{gateway-id}/sensor-id/{sensor-id}/type-en-name/{type-en-name}/limit/{limit}")
    public ResponseEntity<List<ThresholdInfoResponse>> getLatestThresholdsBySensorDataWithLimit(
            @PathVariable("gateway-id") Long gatewayId,
            @PathVariable("sensor-id") String sensorId,
            @PathVariable("type-en-name") String typeEnName,
            @PathVariable("limit") Integer limit
    ) {
        return ResponseEntity
                .ok(
                        thresholdHistoryService.getLatestThresholdsBySensorDataWithLimit(
                                gatewayId, sensorId,
                                typeEnName, limit
                        )
                );
    }

    @PostMapping
    public ResponseEntity<Void> registerThresholdHistory(
            @Validated @RequestBody ThresholdHistoryInfo request
    ) {
        thresholdHistoryService.registerRequest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
