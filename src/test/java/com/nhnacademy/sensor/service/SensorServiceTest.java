package com.nhnacademy.sensor.service;

import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorIndexResponse;
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

    /*@DisplayName("Sensor 서비스: sensorNo")
    @Test
    void testGetSensor_success() {
        /// given
        Sensor mockSensor = testSensor();
        Optional<Sensor> mockOptionalSensor = Optional.of(mockSensor);

        Mockito.when(sensorRepository.findById(mockSensor.getSensorNo()))
                .thenReturn(mockOptionalSensor);

        /// when
        Sensor sensor = sensorService.getSensor(
                mockSensor.getGatewayId(),
                mockSensor.getSensorId()
        );
        log.debug("find sensor: {}", sensor);

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findById(mockSensor.getSensorNo());

        Assertions.assertAll(
                () -> Assertions.assertEquals(mockSensor.getSensorNo(), sensor.getSensorNo()),
                () -> Assertions.assertEquals(mockSensor.getGatewayId(), sensor.getGatewayId()),
                () -> Assertions.assertEquals(mockSensor.getSensorId(), sensor.getSensorId()),
                () -> Assertions.assertEquals(mockSensor.getSensorLocation(), sensor.getSensorLocation()),
                () -> Assertions.assertEquals(mockSensor.getSensorSpot(), sensor.getSensorSpot())
        );
    }*/

    /*@DisplayName("Sensor 서비스: sensorNo")
    @Test
    void testGetSensor_failed() {
        /// given
        Mockito.when(sensorRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        /// when
        Assertions.assertThrows(
                SensorNotFoundException.class,
                () -> sensorService.getSensor("", "")
        );

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findById(sensorNo);
    }*/

    @DisplayName("Sensor Service: ")
    @Test
    void testGetSensorIndexes_success() {
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

        responses.forEach(response ->
                Assertions.assertTrue(mockResponse.contains(response))
        );
    }

    @DisplayName("Sensor Service: ")
    @Test
    void testGetSensorIndexes_empty() {

    }

    private Sensor testSensor() {
        Sensor sensor = Sensor.ofNewSensor(
                SensorTestingData.TEST_GATEWAY_ID,
                SensorTestingData.TEST_SENSOR_ID,
                SensorTestingData.TEST_SENSOR_LOCATION,
                SensorTestingData.TEST_SENSOR_SPOT
        );
        ReflectionTestUtils.setField(sensor, "sensorNo", 1);
        return sensor;
    }

    private Set<String> testSensorIds() {
        int index = -1;
        String testSensorId = SensorTestingData.TEST_SENSOR_ID;
        return Set.of(
                "%s-%d".formatted(testSensorId, ++index),
                "%s-%d".formatted(testSensorId, ++index),
                "%s-%d".formatted(testSensorId, ++index)
        );
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
