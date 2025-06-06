package com.nhnacademy.sensor.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.domain.Sensor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.stream.Stream;

@Slf4j
@CustomDataJpaTest
class SensorRepositoryScenarioTest {

    @Autowired
    private SensorRepository sensorRepository;

    private Sensor sample;

    @BeforeEach
    void setUp() {
        sample = SensorTestingData.sample();
        sensorRepository.save(sample);
    }

    @AfterEach
    void tearDown() {
        sample = null;
    }

    @DisplayName("Sensor JPA: 서로 다른 gatewayId 및 sensorId 조합이면 저장 성공")
    @ParameterizedTest
    @MethodSource("testSensorData")
    void testCreate_unique_success(long gatewayId, String sensorId) {
        Assertions.assertDoesNotThrow(
                () -> sensorRepository.save(
                        Sensor.ofNewSensor(
                                gatewayId,
                                sensorId,
                                SensorTestingData.TEST_SENSOR_NAME,
                                null,
                                null
                        )
                )
        );
    }

    private static Stream<Arguments> testSensorData() {
        long otherGatewayId = 1L + SensorTestingData.TEST_GATEWAY_ID;
        String otherSensorId = "other-sensor-id";
        return Stream.of(
                Arguments.of(otherGatewayId, otherSensorId),
                Arguments.of(otherGatewayId, SensorTestingData.TEST_SENSOR_ID),
                Arguments.of(SensorTestingData.TEST_GATEWAY_ID, otherSensorId)
        );
    }

    @DisplayName("Sensor JPA: 동일한 gatewayId + sensorId 저장 시, 무결성 제약 위반 예외 발생 테스트")
    @Test
    void testCreate_unique_failed() {
        Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> sensorRepository.save(
                        Sensor.ofNewSensor(
                                SensorTestingData.TEST_GATEWAY_ID,
                                SensorTestingData.TEST_SENSOR_ID,
                                SensorTestingData.TEST_SENSOR_NAME,
                                null,
                                null
                        )
                )
        );
    }

    @DisplayName("Sensor JPA: 데이터 조회 테스트(gatewayId & sensorId)")
    @ParameterizedTest
    @MethodSource("testGatewayIdAndSensorIdData")
    void testFindByGatewayIdAndSensorId(long gatewayId, String sensorId) {
        Sensor actual = sensorRepository.findByGatewayIdAndSensorId(gatewayId, sensorId);

        if (isExists(gatewayId, sensorId)) {
            equals(sample, actual);
        } else {
            Assertions.assertNull(actual);
        }
    }

    @DisplayName("Sensor JPA: 데이터의 존재 유무 테스트(gatewayId & sensorId)")
    @ParameterizedTest
    @MethodSource("testGatewayIdAndSensorIdData")
    void testExistsByGatewayIdAndSensorId(long gatewayId, String sensorId) {
        Assertions.assertEquals(
                isExists(gatewayId, sensorId),
                sensorRepository.existsByGatewayIdAndSensorId(gatewayId, sensorId)
        );
    }

    private static Stream<Arguments> testGatewayIdAndSensorIdData() {
        long otherGatewayId = 1L + SensorTestingData.TEST_GATEWAY_ID;
        String otherSensorId = "other-sensor-id";
        return Stream.of(
                Arguments.of(SensorTestingData.TEST_GATEWAY_ID, SensorTestingData.TEST_SENSOR_ID),
                Arguments.of(SensorTestingData.TEST_GATEWAY_ID, otherSensorId),
                Arguments.of(otherGatewayId, SensorTestingData.TEST_SENSOR_ID),
                Arguments.of(otherGatewayId, otherSensorId)
        );
    }

    private boolean isExists(long gatewayId, String sensorId) {
        return sample.getGatewayId().equals(gatewayId)
                && sample.getSensorId().equals(sensorId);
    }

    private void equals(Sensor expected, Sensor actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getGatewayId(), actual.getGatewayId()),
                () -> Assertions.assertEquals(expected.getSensorId(), actual.getSensorId()),
                () -> Assertions.assertEquals(expected.getSensorLocation(), actual.getSensorLocation()),
                () -> Assertions.assertEquals(expected.getSensorSpot(), actual.getSensorSpot())
        );
    }
}
