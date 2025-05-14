package com.nhnacademy.sensor.service;

import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor.service.impl.SensorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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

    /*@DisplayName("Sensor 서비스: 모든 sensor의 sensorId 값을 Set 형태로 반환")
    @Test
    void testGetDistinctSensorIds() {
        /// given
        Set<String> mockSensorIds = testSensorIds();
        Mockito.when(sensorRepository.findDistinctSensorIds())
                .thenReturn(mockSensorIds);

        /// when
        Set<String> sensorIds = sensorService.getDistinctSensorIds();
        log.debug("find sensorIds: {}", sensorIds);

        /// then
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findDistinctSensorIds();

        assertThat(sensorIds).containsExactlyInAnyOrderElementsOf(mockSensorIds);
    }*/

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
}
