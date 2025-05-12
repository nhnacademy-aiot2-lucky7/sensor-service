package com.nhnacademy.sensor.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@CustomDataJpaTest
class CustomSensorRepositoryTest {

    private static final String TEST_GATEWAY_ID = "test-gateway-id";

    private static final String TEST_SENSOR_ID = "test-sensor-id";

    private static final String TEST_SENSOR_LOCATION = "test-sensor-location";

    private static final String TEST_SENSOR_SPOT = "test-sensor-spot";

    @Autowired
    private SensorRepository sensorRepository;

    @DisplayName("QueryDSL: 존재하는 센서 아이디 카운트 체크")
    @Test
    void testCountBySensorId_save() {
        Sensor sensor = Sensor.ofNewSensor(
                TEST_GATEWAY_ID, TEST_SENSOR_ID,
                TEST_SENSOR_LOCATION, TEST_SENSOR_SPOT
        );
        sensorRepository.save(sensor);

        Assertions.assertNotEquals(
                0,
                sensorRepository.countBySensorId(sensor.getSensorId())
        );
    }

    @DisplayName("QueryDSL: 존재하지 않는 센서 아이디 카운트 체크")
    @Test
    void testCountBySensorId_notSave() {
        Assertions.assertEquals(
                0,
                sensorRepository.countBySensorId(TEST_SENSOR_ID)
        );
    }

    @DisplayName("QueryDSL: 존재하는 센서 아이디 중복 체크")
    @Test
    void testExistsBySensorId_save() {
        Sensor sensor = Sensor.ofNewSensor(
                TEST_GATEWAY_ID, TEST_SENSOR_ID,
                TEST_SENSOR_LOCATION, TEST_SENSOR_SPOT
        );
        sensorRepository.save(sensor);

        Assertions.assertTrue(
                sensorRepository.existsBySensorId(sensor.getSensorId())
        );
    }

    @DisplayName("QueryDSL: 존재하지 않는 센서 아이디 중복 체크")
    @Test
    void testExistsBySensorId_notSave() {
        Assertions.assertFalse(
                sensorRepository.existsBySensorId(TEST_SENSOR_ID)
        );
    }

    @DisplayName("QueryDSL: 모든 Entity 데이터들에서 센서 ID만 가져와 Set<sensorId> 처리")
    @ParameterizedTest
    @MethodSource("sensors")
    void testFindDistinctSensorIds(List<Sensor> sensors) {
        sensorRepository.saveAll(sensors);

        Set<String> findSensorIds = sensorRepository.findDistinctSensorIds();
        log.debug("sensors: {}", findSensorIds);
        Assertions.assertEquals(sensors.size(), findSensorIds.size());

        findSensorIds.forEach(sensorId -> {
            SensorInfoResponse actual = sensorRepository.findBySensorId(sensorId);
            log.debug("sensor: {}", actual);

            Assertions.assertNotNull(actual);
            Assertions.assertNotEquals(
                    0,
                    sensors.stream()
                            .filter(sensor ->
                                    sensor.getSensorId().equals(actual.getSensorId())
                            )
                            .count()
            );
        });
    }

    private static Stream<Arguments> sensors() {
        List<Sensor> sensors = new ArrayList<>();
        for (int n = 1; n < 6; n++) {
            sensors.add(
                    Sensor.ofNewSensor(
                            TEST_GATEWAY_ID, "%s-%d".formatted(TEST_SENSOR_ID, n),
                            TEST_SENSOR_LOCATION, TEST_SENSOR_SPOT
                    )
            );
        }
        return Stream.of(
                Arguments.of(sensors)
        );
    }
}
