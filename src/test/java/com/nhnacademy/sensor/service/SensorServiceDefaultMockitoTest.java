package com.nhnacademy.sensor.service;

import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor.service.impl.SensorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
class SensorServiceDefaultMockitoTest {

    private SensorRepository sensorRepository;

    private SensorService sensorService;

    @BeforeEach
    void setUp() {
        sensorRepository = Mockito.mock(SensorRepository.class);
        sensorService = new SensorServiceImpl(sensorRepository);
    }

    @DisplayName("Sensor 서비스: 모든 sensor의 sensorId 값을 Set 형태로 반환")
    @Test
    void testGetSensorIds() {
        /// given
        Set<String> mockSensorIds = testSensorIds();
        Mockito.when(sensorRepository.findDistinctSensorIds()).thenReturn(mockSensorIds);

        /// when
        Set<String> sensorIds = sensorService.getSensorIds();
        log.debug("find sensorIds: {}", sensorIds);

        /// then
        assertThat(sensorIds).containsExactlyInAnyOrderElementsOf(mockSensorIds);
        Mockito.verify(sensorRepository, Mockito.times(1)).findDistinctSensorIds();
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
