package com.nhnacademy.sensor.service.impl;

import com.nhnacademy.common.exception.http.extend.SensorAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.SensorNotFoundException;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorIndexResponse;
import com.nhnacademy.sensor.dto.SensorInfo;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorNameUpdateRequest;
import com.nhnacademy.sensor.dto.SensorPositionUpdateRequest;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor.service.SensorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
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
        String sensorName = "%s-%d".formatted(
                UUID.randomUUID().toString().substring(0, 8),
                System.currentTimeMillis()
        );

        return sensorRepository.save(
                Sensor.ofNewSensor(
                        request.getGatewayId(),
                        request.getSensorId(),
                        sensorName,
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

    public Sensor getSensor(long gatewayId, String sensorId) {
        Sensor sensor = sensorRepository.findByGatewayIdAndSensorId(
                gatewayId,
                sensorId
        );
        if (sensor == null) {
            throw new SensorNotFoundException(
                    gatewayId,
                    sensorId
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
    public void updateSensorName(SensorNameUpdateRequest request) {
        Sensor sensor = getSensor(
                request.getGatewayId(),
                request.getSensorId()
        );
        sensor.updateSensorName(request.getSensorName());
        sensorRepository.flush();
    }

    @Override
    public void updateSensorPosition(SensorPositionUpdateRequest request) {
        Sensor sensor = getSensor(
                request.getGatewayId(),
                request.getSensorId()
        );
        sensor.updateSensorPosition(
                request.getLocation(),
                request.getSpot()
        );
        sensorRepository.flush();
    }

    @Override
    public void removeSensor(SensorInfo request) {
        Sensor sensor = getSensor(request);
        sensorRepository.delete(sensor);
        sensorRepository.flush();
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
