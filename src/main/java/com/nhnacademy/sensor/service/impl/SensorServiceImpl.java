package com.nhnacademy.sensor.service.impl;

import com.nhnacademy.common.exception.http.extend.SensorAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.SensorNotFoundException;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorDataHandlerResponse;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.sensor.dto.SensorSearchRequest;
import com.nhnacademy.sensor.dto.SensorUpdateRequest;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor.service.SensorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public void registerRequest(SensorRegisterRequest request) {
        if (isExistsSensor(request.getGatewayId(), request.getSensorId())) {
            throw new SensorAlreadyExistsException(request);
        }
        registerSensor(
                request.getGatewayId(), request.getSensorId(),
                request.getSensorLocation(), request.getSensorSpot()
        );
    }

    @Override
    public Sensor registerSensor(String gatewayId, String sensorId, String sensorLocation, String sensorSpot) {
        return sensorRepository.save(
                Sensor.ofNewSensor(
                        gatewayId, sensorId,
                        sensorLocation, sensorSpot
                )
        );
    }

    @Override
    public Sensor getSensor(String gatewayId, String sensorId) {
        Sensor sensor = sensorRepository.findByGatewayIdAndSensorId(gatewayId, sensorId);
        if (sensor == null) {
            throw new SensorNotFoundException(gatewayId, sensorId);
        }
        return sensor;
    }

    @Override
    public Sensor getReferenceSensor(String gatewayId, String sensorId) {
        return sensorRepository.getReferenceByGatewayIdAndSensorId(gatewayId, sensorId);
    }

    @Override
    public void updateSensor(SensorUpdateRequest request) {
        Sensor sensor = getSensor(request.getGatewayId(), request.getSensorId());
        sensor.updateSensorPosition(request.getSensorLocation(), request.getSensorSpot());
        sensorRepository.flush();
    }

    @Override
    public void removeSensor(String gatewayId, String sensorId) {
        sensorRepository.delete(getSensor(gatewayId, sensorId));
    }

    @Override
    public boolean isExistsSensor(String gatewayId, String sensorId) {
        return sensorRepository.existsByGatewayIdAndSensorId(gatewayId, sensorId);
    }

    @Transactional(readOnly = true)
    @Override
    public SensorInfoResponse getSensorInfoResponse(String gatewayId, String sensorId) {
        return SensorInfoResponse.from(getSensor(gatewayId, sensorId));
    }

    /// TODO: 아직 구현된 로직이 타당한지는 확정되지 않았습니다.
    @Transactional(readOnly = true)
    @Override
    public List<SensorInfoResponse> getSearchSensorInfoResponse(String gatewayId) {
        return sensorRepository.findByConditions(
                new SensorSearchRequest(
                        gatewayId, null, null, null
                )
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Set<SensorDataHandlerResponse> getSensorUniqueKeys() {
        return sensorRepository.findAllSensorUniqueKeys();
    }
}
