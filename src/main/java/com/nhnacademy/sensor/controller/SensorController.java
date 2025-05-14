package com.nhnacademy.sensor.controller;

import com.nhnacademy.sensor.dto.SensorDataHandlerResponse;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.sensor.dto.SensorUpdateRequest;
import com.nhnacademy.sensor.service.SensorService;
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
import java.util.Set;

@RestController
@RequestMapping("/sensor-service/api/v1/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/gateway-id/{gateway-id}")
    public ResponseEntity<List<SensorInfoResponse>> getSensorInfoResponse(
            @PathVariable("gateway-id") String gatewayId
    ) {
        return ResponseEntity
                .ok(sensorService.getSearchSensorInfoResponse(gatewayId));
    }

    @GetMapping("/gateway-id/{gateway-id}/sensor-id/{sensor-id}")
    public ResponseEntity<SensorInfoResponse> getSensorInfoResponse(
            @PathVariable("gateway-id") String gatewayId,
            @PathVariable("sensor-id") String sensorId
    ) {
        return ResponseEntity
                .ok(sensorService.getSensorInfoResponse(gatewayId, sensorId));
    }

    @GetMapping("/distinct-sensor-id-set")
    public ResponseEntity<Set<SensorDataHandlerResponse>> getSensorUniqueKeys() {
        return ResponseEntity
                .ok(sensorService.getSensorUniqueKeys());
    }

    @PostMapping
    public ResponseEntity<Void> registerSensor(
            @Validated @RequestBody SensorRegisterRequest request
    ) {
        sensorService.registerRequest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping
    public ResponseEntity<Void> updateSensor(
            @Validated @RequestBody SensorUpdateRequest request
    ) {
        sensorService.updateSensor(request);
        return ResponseEntity
                .noContent()
                .build();
    }

    /// TODO: 삭제의 경우는 SensorDataMapping랑 연계해서 삭제해야 합니다.
}
