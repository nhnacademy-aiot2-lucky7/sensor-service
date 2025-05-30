package com.nhnacademy.sensor_type_mapping.controller;

import com.nhnacademy.sensor_type_mapping.dto.SearchNoRequest;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfo;
import com.nhnacademy.sensor_type_mapping.service.SensorDataMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/sensor-data-mappings")
public class SensorDataMappingController {

    private final SensorDataMappingService sensorDataMappingService;

    public SensorDataMappingController(SensorDataMappingService sensorDataMappingService) {
        this.sensorDataMappingService = sensorDataMappingService;
    }

    @GetMapping
    public ResponseEntity<Set<SensorDataMappingIndexResponse>> getIndexes() {
        return ResponseEntity
                .ok(sensorDataMappingService.getIndexes());
    }

    @GetMapping("/search-status")
    public ResponseEntity<List<SensorDataMappingAiResponse>> getStatuses(
            @RequestParam(required = false) List<String> status
    ) {
        return ResponseEntity
                .ok(sensorDataMappingService.getStatuses(status));
    }

    /// TODO: P.K 값을 제물로 바쳐서 gatewayId, SensorId, DataType 3신기 소환
    @GetMapping("/sensor-data-no/{sensor-data-no}")
    public ResponseEntity<Void> getIndex(
            @PathVariable("sensor-data-no") Long sensorDataNo
    ) {
        return ResponseEntity
                .noContent()
                .build();
    }

    // gatewayId에 해당하는 모든 센서들의 정보 조회
    @GetMapping("/gateway-id/{gateway_id}")
    public ResponseEntity<List<SensorDataMappingAiResponse>> getList(
            @PathVariable("gateway_id") Long gatewayId
    ) {
        return ResponseEntity
                .ok(sensorDataMappingService.getAiResponse(gatewayId));
    }

    // P.K 값 검색
    @PostMapping("/find-no")
    public ResponseEntity<SearchNoResponse> getId(
            @Validated @RequestBody SearchNoRequest request
    ) {
        return ResponseEntity
                .ok(sensorDataMappingService.getSearchNoResponse(request));
    }

    @PostMapping
    public ResponseEntity<Void> registerSensorDataMapping(
            @Validated @RequestBody SensorDataMappingInfo request
    ) {
        sensorDataMappingService.registerRequest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping
    public ResponseEntity<Void> updateSensorDataMapping(
            @Validated @RequestBody SensorDataMappingInfo request
    ) {
        sensorDataMappingService.updateSensorDataMapping(request);
        return ResponseEntity
                .noContent()
                .build();
    }

    /// TODO: 삭제 기능 구현 예정...
}
