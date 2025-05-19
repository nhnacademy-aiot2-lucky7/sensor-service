package com.nhnacademy.sensor.service;

import com.nhnacademy.common.exception.http.extend.SensorAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.SensorNotFoundException;
import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorIndexResponse;
import com.nhnacademy.sensor.dto.SensorInfo;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor.service.impl.SensorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl sensorService;

    @DisplayName("Sensor Service: ")
    @Test
    void testRegisterRequest_success() {
        /// given
        SensorInfo mockRequest = testSensorInfo();
        Sensor mockSensor = testSensor();
        Sensor mockSensorResult = testSensorContext();

        Mockito.when(
                        sensorRepository.existsByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(false);

        Mockito.when(sensorRepository.save(mockSensor))
                .thenReturn(mockSensorResult);

        /// when
        Assertions.assertDoesNotThrow(
                () -> sensorService.registerRequest(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .existsByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .save(mockSensor);
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testRegisterRequest_failed() {
        /// given
        SensorInfo mockRequest = testSensorInfo();

        Mockito.when(
                        sensorRepository.existsByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(true);

        /// when
        Assertions.assertThrows(
                SensorAlreadyExistsException.class,
                () -> sensorService.registerRequest(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .existsByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Mockito.verify(
                        sensorRepository,
                        Mockito.never()
                )
                .save(Mockito.any(Sensor.class));
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testRegisterSensor_success() {
        /// given
        SensorInfo mockRequest = testSensorInfo();
        Sensor mockSensor = testSensor();
        Sensor mockSensorResult = testSensorContext();

        Mockito.when(sensorRepository.save(mockSensor))
                .thenReturn(mockSensorResult);

        /// when
        Sensor registerSensor = sensorService.registerSensor(mockRequest);
        log.debug("registerSensor result: {}", registerSensor);

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .save(mockSensor);

        Assertions.assertEquals(mockSensorResult, registerSensor);
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testRegisterSensor_failed() {
        /// given
        SensorInfo mockRequest = testSensorInfo();
        Sensor mockSensor = testSensor();

        Mockito.when(sensorRepository.save(mockSensor))
                .thenThrow(DataIntegrityViolationException.class);

        /// when
        Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> sensorService.registerSensor(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .save(mockSensor);
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testGetSensor_success() {
        /// given
        SensorInfo mockRequest = testSensorInfo();
        Sensor mockSensorResult = testSensorContext();

        Mockito.when(
                        sensorRepository.findByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(mockSensorResult);

        /// when
        Sensor result = sensorService.getSensor(mockRequest);
        log.debug("getSensor result: {}", result);

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Assertions.assertEquals(mockSensorResult, result);
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testGetSensor_failed() {
        /// given
        SensorInfo mockRequest = testSensorInfo();

        Mockito.when(
                        sensorRepository.findByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(null);

        /// when
        Assertions.assertThrows(
                SensorNotFoundException.class,
                () -> sensorService.getSensor(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testUpdateSensor_success() {
        /// given
        Sensor mockSensorResult = testSensorContext();
        SensorInfo mockRequest = new SensorInfo(
                mockSensorResult.getGatewayId(),
                mockSensorResult.getSensorId(),
                "new-sensor-location",
                "new-sensor-spot"
        );

        Mockito.when(
                        sensorRepository.findByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(mockSensorResult);

        /// when
        Assertions.assertDoesNotThrow(
                () -> sensorService.updateSensor(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .flush();

        Assertions.assertAll(
                () -> Assertions.assertEquals(mockRequest.getSensorLocation(), mockSensorResult.getSensorLocation()),
                () -> Assertions.assertEquals(mockRequest.getSensorSpot(), mockSensorResult.getSensorSpot())
        );
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testUpdateSensor_failed() {
        /// given
        Sensor mockSensorResult = testSensorContext();
        SensorInfo mockRequest = new SensorInfo(
                mockSensorResult.getGatewayId(),
                mockSensorResult.getSensorId(),
                "new-sensor-location",
                "new-sensor-spot"
        );

        Mockito.when(
                        sensorRepository.findByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(null);

        /// when
        Assertions.assertThrows(
                SensorNotFoundException.class,
                () -> sensorService.updateSensor(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Mockito.verify(
                        sensorRepository,
                        Mockito.never()
                )
                .flush();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(mockRequest.getSensorLocation(), mockSensorResult.getSensorLocation()),
                () -> Assertions.assertNotEquals(mockRequest.getSensorSpot(), mockSensorResult.getSensorSpot())
        );
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testRemoveSensor_success() {
        /// given
        SensorInfo mockRequest = testSensorInfo();
        Sensor mockSensorResult = testSensorContext();

        Mockito.when(
                        sensorRepository.findByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(mockSensorResult);

        /// when
        Assertions.assertDoesNotThrow(
                () -> sensorService.removeSensor(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .delete(mockSensorResult);

        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .flush();
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testRemoveSensor_failed() {
        /// given
        SensorInfo mockRequest = testSensorInfo();

        Mockito.when(
                        sensorRepository.findByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(null);

        /// when
        Assertions.assertThrows(
                SensorNotFoundException.class,
                () -> sensorService.removeSensor(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Mockito.verify(
                        sensorRepository,
                        Mockito.never()
                )
                .delete(Mockito.any(Sensor.class));

        Mockito.verify(
                        sensorRepository,
                        Mockito.never()
                )
                .flush();
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testIsExistsSensor_true() {
        /// given
        SensorInfo mockRequest = testSensorInfo();

        Mockito.when(
                        sensorRepository.existsByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(true);

        /// when
        boolean isExistsSensor = sensorService.isExistsSensor(mockRequest);
        log.debug("isExistsSensor true?: {}", isExistsSensor);

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .existsByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Assertions.assertTrue(isExistsSensor);
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testIsExistsSensor_false() {
        /// given
        SensorInfo mockRequest = testSensorInfo();

        Mockito.when(
                        sensorRepository.existsByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(false);

        /// when
        boolean isExistsSensor = sensorService.isExistsSensor(mockRequest);
        log.debug("isExistsSensor false?: {}", !isExistsSensor);

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .existsByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Assertions.assertFalse(isExistsSensor);
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testGetSensorInfoResponse_success() {
        /// given
        SensorInfo mockRequest = testSensorInfo();
        Sensor mockSensorResult = testSensorContext();

        Mockito.when(
                        sensorRepository.findByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(mockSensorResult);

        /// when
        SensorInfoResponse response = sensorService.getSensorInfoResponse(mockRequest);
        log.debug("getSensorInfoResponse result: {Key: {} / Value: {}}", response.getGatewayId(), response.getSensorId());

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );

        Assertions.assertAll(
                () -> Assertions.assertEquals(mockSensorResult.getGatewayId(), response.getGatewayId()),
                () -> Assertions.assertEquals(mockSensorResult.getSensorId(), response.getSensorId()),
                () -> Assertions.assertEquals(mockSensorResult.getSensorLocation(), response.getSensorLocation()),
                () -> Assertions.assertEquals(mockSensorResult.getSensorSpot(), response.getSensorSpot())
        );
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testGetSensorInfoResponse_failed() {
        SensorInfo mockRequest = testSensorInfo();

        Mockito.when(
                        sensorRepository.findByGatewayIdAndSensorId(
                                mockRequest.getGatewayId(),
                                mockRequest.getSensorId()
                        )
                )
                .thenReturn(null);

        /// when
        Assertions.assertThrows(
                SensorNotFoundException.class,
                () -> sensorService.getSensorInfoResponse(mockRequest)
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findByGatewayIdAndSensorId(
                        mockRequest.getGatewayId(),
                        mockRequest.getSensorId()
                );
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testGetSensorIndexes_exists() {
        /// given
        Set<SensorIndexResponse> mockResponse = testSensorIndexes();
        Mockito.when(sensorRepository.findAllSensorUniqueKeys())
                .thenReturn(mockResponse);

        /// when
        Set<SensorIndexResponse> responses = sensorService.getSensorIndexes();
        responses.forEach(response ->
                log.debug("Key({}): Value({})", response.getGatewayId(), response.getSensorId())
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findAllSensorUniqueKeys();

        Assertions.assertEquals(mockResponse.size(), responses.size());
        responses.forEach(response ->
                Assertions.assertTrue(mockResponse.contains(response))
        );
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testGetSensorIndexes_empty() {
        /// given
        Mockito.when((sensorRepository.findAllSensorUniqueKeys()))
                .thenReturn(Set.of());

        /// when
        Set<SensorIndexResponse> responses = sensorService.getSensorIndexes();

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findAllSensorUniqueKeys();

        Assertions.assertTrue(responses.isEmpty());
    }

    private SensorInfo testSensorInfo() {
        return new SensorInfo(
                SensorTestingData.TEST_GATEWAY_ID,
                SensorTestingData.TEST_SENSOR_ID,
                SensorTestingData.TEST_SENSOR_LOCATION,
                SensorTestingData.TEST_SENSOR_SPOT
        );
    }

    private Sensor testSensor() {
        SensorInfo request = testSensorInfo();
        return Sensor.ofNewSensor(
                request.getGatewayId(),
                request.getSensorId(),
                request.getSensorLocation(),
                request.getSensorSpot()
        );
    }

    private Sensor testSensorContext() {
        Sensor sensor = testSensor();
        ReflectionTestUtils.setField(sensor, "sensorNo", 1);
        return sensor;
    }

    private Set<SensorIndexResponse> testSensorIndexes() {
        return SensorTestingData.samples()
                .stream()
                .map(sensor ->
                        new SensorIndexResponse(
                                sensor.getGatewayId(),
                                sensor.getSensorId()
                        )
                )
                .collect(Collectors.toSet());
    }
}
