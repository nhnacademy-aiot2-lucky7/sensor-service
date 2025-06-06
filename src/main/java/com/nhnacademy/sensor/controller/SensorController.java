package com.nhnacademy.sensor.controller;

import com.nhnacademy.sensor.dto.SensorIndexResponse;
import com.nhnacademy.sensor.dto.SensorInfo;
import com.nhnacademy.sensor.dto.SensorNameUpdateRequest;
import com.nhnacademy.sensor.dto.SensorPositionUpdateRequest;
import com.nhnacademy.sensor.service.SensorService;
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
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public ResponseEntity<Set<SensorIndexResponse>> getSensorIndexes() {
        return ResponseEntity
                .ok(sensorService.getSensorIndexes());
    }

    @PostMapping
    public ResponseEntity<Void> registerSensor(
            @Validated @RequestBody SensorInfo request
    ) {
        sensorService.registerRequest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/update-sensor-name")
    public ResponseEntity<Void> updateSensorName(
            @Validated @RequestBody SensorNameUpdateRequest request
    ) {
        sensorService.updateSensorName(request);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/update-sensor-position")
    public ResponseEntity<Void> updateSensorPosition(
            @Validated @RequestBody SensorPositionUpdateRequest request
    ) {
        sensorService.updateSensorPosition(request);
        return ResponseEntity
                .noContent()
                .build();
    }
    
    /// TODO: 삭제의 경우는 SensorDataMapping랑 연계해서 삭제해야 합니다.
}
