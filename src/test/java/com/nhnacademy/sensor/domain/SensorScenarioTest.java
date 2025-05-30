package com.nhnacademy.sensor.domain;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.SensorTestingData;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

@Slf4j
@CustomDataJpaTest
class SensorScenarioTest {

    @Autowired
    private EntityManager em;

    private Sensor sample;

    @BeforeEach
    void setUp() {
        sample = SensorTestingData.sample();
        em.persist(sample);
    }

    @AfterEach
    void tearDown() {
        sample = null;
    }

    @DisplayName("Sensor Entity: 서로 다른 gatewayId 및 sensorId 조합이면 저장 성공")
    @ParameterizedTest
    @MethodSource("testSensorData")
    void testUnique_success(long gatewayId, String sensorId) {
        Assertions.assertDoesNotThrow(
                () -> em.persist(
                        Sensor.ofNewSensor(
                                gatewayId,
                                sensorId,
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

    @DisplayName("Sensor Entity: 동일한 gatewayId + sensorId 저장 시, 무결성 제약 위반 예외 발생 테스트")
    @Test
    void testUnique_failed() {
        Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> em.persist(
                        Sensor.ofNewSensor(
                                SensorTestingData.TEST_GATEWAY_ID,
                                SensorTestingData.TEST_SENSOR_ID,
                                null,
                                null
                        )
                )
        );
    }

    @DisplayName("Sensor Entity: 센서 위치(sensorLocation, sensorSpot) 업데이트 값이 null 또는 공백일 경우 기존 값 유지")
    @ParameterizedTest
    @MethodSource("testPositionData")
    void testUpdateSensorPosition(String sensorLocation, String sensorSpot) {
        sample.updateSensorPosition(sensorLocation, sensorSpot);
        em.flush();
        em.clear();

        Sensor actual = em.find(Sensor.class, sample.getSensorNo());
        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        (sensorLocation != null && !sensorLocation.isBlank()) ?
                                sensorLocation
                                : SensorTestingData.TEST_SENSOR_LOCATION,
                        actual.getSensorLocation()
                ),

                () -> Assertions.assertEquals(
                        (sensorSpot != null && !sensorSpot.isBlank()) ?
                                sensorSpot
                                : SensorTestingData.TEST_SENSOR_SPOT,
                        actual.getSensorSpot()
                )
        );
    }

    private static Stream<Arguments> testPositionData() {
        String sensorLocation = "클래스 A";
        String sensorSpot = "후문";
        String blank = "";
        String spaceFilledBlank = "     ";
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, blank),
                Arguments.of(null, spaceFilledBlank),
                Arguments.of(blank, null),
                Arguments.of(spaceFilledBlank, null),
                Arguments.of(blank, blank),
                Arguments.of(blank, spaceFilledBlank),
                Arguments.of(spaceFilledBlank, blank),
                Arguments.of(spaceFilledBlank, spaceFilledBlank),
                Arguments.of(sensorLocation, null),
                Arguments.of(sensorLocation, blank),
                Arguments.of(sensorLocation, spaceFilledBlank),
                Arguments.of(null, sensorSpot),
                Arguments.of(blank, sensorSpot),
                Arguments.of(spaceFilledBlank, sensorSpot),
                Arguments.of(sensorLocation, sensorSpot)
        );
    }
}
