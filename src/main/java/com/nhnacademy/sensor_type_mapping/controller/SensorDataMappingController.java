package com.nhnacademy.sensor_type_mapping.controller;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMappingInfo;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.service.SensorDataMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/sensor-data-mappings")
public class SensorDataMappingController {

    private final SensorDataMappingService sensorDataMappingService;

    public SensorDataMappingController(SensorDataMappingService sensorDataMappingService) {
        this.sensorDataMappingService = sensorDataMappingService;
    }

    @GetMapping
    public ResponseEntity<Set<SensorDataMappingIndexResponse>> getSensorDataMappingIndexes() {
        return ResponseEntity
                .ok(sensorDataMappingService.getSensorDataMappingIndexes());
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

    /// TODO: 테스트
    @PostMapping("/test")
    public ResponseEntity<SensorDataMappingInfo> get(
            @Validated @RequestBody SensorDataMappingInfo test
    ) {
        return ResponseEntity
                .ok(test);
    }
}
