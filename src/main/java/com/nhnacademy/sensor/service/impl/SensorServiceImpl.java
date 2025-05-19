package com.nhnacademy.sensor.service.impl;

import com.nhnacademy.common.exception.http.extend.SensorAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.SensorNotFoundException;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorIndexResponse;
import com.nhnacademy.sensor.dto.SensorInfo;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor.service.SensorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public void registerRequest(SensorInfo request) {
        if (isExistsSensor(request)) {
            throw new SensorAlreadyExistsException(
                    request.getGatewayId(),
                    request.getSensorId()
            );
        }
        registerSensor(request);
    }

    @Override
    public Sensor registerSensor(SensorInfo request) {
        return sensorRepository.save(
                Sensor.ofNewSensor(
                        request.getGatewayId(),
                        request.getSensorId(),
                        request.getSensorLocation(),
                        request.getSensorSpot()
                )
        );
    }

    @Override
    public Sensor getSensor(SensorInfo request) {
        Sensor sensor = sensorRepository.findByGatewayIdAndSensorId(
                request.getGatewayId(),
                request.getSensorId()
        );
        if (sensor == null) {
            throw new SensorNotFoundException(
                    request.getGatewayId(),
                    request.getSensorId()
            );
        }
        return sensor;
    }

    @Override
    public Sensor getReferenceSensor(SensorInfo request) {
        return sensorRepository.getReferenceByGatewayIdAndSensorId(
                request.getGatewayId(),
                request.getSensorId()
        );
    }

    @Override
    public void updateSensor(SensorInfo request) {
        Sensor sensor = getSensor(request);
        sensor.updateSensorPosition(
                request.getSensorLocation(),
                request.getSensorSpot()
        );
        sensorRepository.flush();
    }

    @Override
    public void removeSensor(SensorInfo request) {
        Sensor sensor = getSensor(request);
        sensorRepository.delete(sensor);
    }

    @Override
    public boolean isExistsSensor(SensorInfo request) {
        return sensorRepository.existsByGatewayIdAndSensorId(
                request.getGatewayId(),
                request.getSensorId()
        );
    }

    /// 상세 정보 데이터
    @Transactional(readOnly = true)
    @Override
    public SensorInfoResponse getSensorInfoResponse(SensorInfo request) {
        Sensor sensor = getSensor(request);
        return SensorInfoResponse.from(sensor);
    }

    /// 검색용 데이터
    @Transactional(readOnly = true)
    @Override
    public Set<SensorIndexResponse> getSensorIndexes() {
        return sensorRepository.findAllSensorUniqueKeys();
    }
}
