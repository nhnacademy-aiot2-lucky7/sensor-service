package com.nhnacademy.sensor.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.domain.Sensor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
@CustomDataJpaTest
class SensorRepositoryTest {

    private static final String TEST_GATEWAY_ID = "test-gateway-id";

    private static final String TEST_SENSOR_ID = "test-sensor-id";

    @Autowired
    private SensorRepository sensorRepository;

    private Sensor test;

    @BeforeEach
    void setUp() {
        test = Sensor.ofNewSensor(TEST_GATEWAY_ID, TEST_SENSOR_ID);
    }

    @DisplayName("JPA: 삽입 테스트")
    @Test
    void testCreate() {
        sensorRepository.save(test);

        log.debug("create actual: {}", test);
        Assertions.assertTrue(sensorRepository.findById(test.getSensorNo()).isPresent());
    }

    @DisplayName("JPA: 조회 테스트")
    @Test
    void testRead() {
        sensorRepository.save(test);

        Sensor actual = get(test.getSensorNo());
        log.debug("read actual: {}", actual);

        Assertions.assertAll(
                () -> Assertions.assertEquals(test.getGatewayId(), actual.getGatewayId()),
                () -> Assertions.assertEquals(test.getSensorId(), actual.getSensorId()),
                () -> Assertions.assertNull(actual.getSensorLocation()),
                () -> Assertions.assertNull(actual.getSensorSpot())
        );
    }

    @DisplayName("JPA: 수정 테스트")
    @Test
    void testUpdate() {
        String location = "클래스 A";
        String spot = "후문";

        sensorRepository.save(test);
        test.updateSensorPosition(location, spot);
        sensorRepository.flush();

        Sensor actual = get(test.getSensorNo());
        log.debug("update actual: {}", actual);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(actual.getSensorLocation()),
                () -> Assertions.assertEquals(test.getSensorLocation(), actual.getSensorLocation()),

                () -> Assertions.assertNotNull(actual.getSensorSpot()),
                () -> Assertions.assertEquals(test.getSensorSpot(), actual.getSensorSpot())
        );
    }

    @DisplayName("JPA: 삭제 테스트")
    @Test
    void testDelete() {
        sensorRepository.save(test);

        Sensor delete = get(test.getSensorNo());
        sensorRepository.deleteById(delete.getSensorNo());
        log.debug("delete actual: {}", delete);

        Optional<Sensor> actual = sensorRepository.findById(test.getSensorNo());
        Assertions.assertTrue(actual.isEmpty());
    }

    private Sensor get(Integer sensorNo) {
        Optional<Sensor> optional = sensorRepository.findById(sensorNo);
        Assertions.assertTrue(optional.isPresent());
        return optional.get();
    }
}