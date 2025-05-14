package com.nhnacademy.sensor_type_mapping.controller;

import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingRegisterRequest;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingSearchRequest;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingUpdateRequest;
import com.nhnacademy.sensor_type_mapping.service.SensorDataMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sensor-service/api/v1/sensor-data-mappings")
public class SensorDataMappingController {

    private final SensorDataMappingService sensorDataMappingService;

    public SensorDataMappingController(SensorDataMappingService sensorDataMappingService) {
        this.sensorDataMappingService = sensorDataMappingService;
    }

    @GetMapping("/gateway-id/{gateway-id}")
    public ResponseEntity<List<SensorDataMappingAiResponse>> getSensorDataMappingAiResponse(
            @PathVariable("gateway-id") String gatewayId
    ) {
        return ResponseEntity
                .ok(
                        sensorDataMappingService
                                .getSensorDataMappingAiResponse(gatewayId)
                );
    }

    @PostMapping("/search")
    public ResponseEntity<List<SensorDataMappingInfoResponse>> search(
            @Validated @RequestBody SensorDataMappingSearchRequest request
    ) {
        return ResponseEntity
                .ok(
                        sensorDataMappingService
                                .getSearchSensorDataMappingInfoResponse(request)
                );
    }

    @PostMapping
    public ResponseEntity<Void> registerSensorDataMapping(
            @Validated @RequestBody SensorDataMappingRegisterRequest request
    ) {
        sensorDataMappingService.registerRequest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping
    public ResponseEntity<Void> updateSensorDataMapping(
            @Validated @RequestBody SensorDataMappingUpdateRequest request
    ) {
        sensorDataMappingService.updateSensorDataMapping(request);
        return ResponseEntity
                .noContent()
                .build();
    }

    /// TODO: 삭제 기능 구현 예정...
}
