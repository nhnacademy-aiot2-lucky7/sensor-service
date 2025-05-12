package com.nhnacademy.sensor.service;

import com.nhnacademy.sensor.SensorTestingData;
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

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl sensorReadService;

    @DisplayName("Sensor 서비스: 모든 sensor의 sensorId 값을 Set 형태로 반환")
    @Test
    void testGetSensorIds() {
        /// given
        Set<String> mockSensorIds = testSensorIds();
        Mockito.when(sensorRepository.findDistinctSensorIds())
                .thenReturn(mockSensorIds);

        /// when
        Set<String> sensorIds = sensorReadService.getSensorIds();
        log.debug("find sensorIds: {}", sensorIds);

        /// then
        assertThat(sensorIds).containsExactlyInAnyOrderElementsOf(mockSensorIds);
        Mockito.verify(
                        sensorRepository,
                        Mockito.times(1)
                )
                .findDistinctSensorIds();
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
