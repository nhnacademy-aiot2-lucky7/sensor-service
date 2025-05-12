package com.nhnacademy.sensor.service.impl;

import com.nhnacademy.common.exception.SensorNotFoundException;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor.service.SensorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Sensor getSensorBySensorNo(int sensorNo) {
        return sensorRepository.findById(sensorNo)
                .orElseThrow(() -> new SensorNotFoundException(sensorNo));
    }

    public SensorInfoResponse getSensorBySensorId(String sensorId) {
        SensorInfoResponse response = sensorRepository.findBySensorId(sensorId);
        if (response == null) {
            throw new SensorNotFoundException(sensorId);
        }
        return response;
    }

    @Override
    public Set<String> getSensorIds() {
        return sensorRepository.findDistinctSensorIds();
    }
}
