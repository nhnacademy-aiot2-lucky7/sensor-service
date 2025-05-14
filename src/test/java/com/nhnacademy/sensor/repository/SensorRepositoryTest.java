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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
@CustomDataJpaTest
class SensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    private Sensor sample;

    @BeforeEach
    void setUp() {
        sample = SensorTestingData.sample();
    }

    @AfterEach
    void tearDown() {
        sample = null;
    }

    @DisplayName("Sensor JPA: 삽입 테스트")
    @Test
    void testCreate() {
        Sensor sensor = sensorRepository.save(sample);
        log.debug("create actual: {}", sensor);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(sensor),
                () -> Assertions.assertTrue(sensorRepository.findById(sample.getSensorNo()).isPresent())
        );
    }

    @DisplayName("Sensor JPA: 조회 테스트")
    @Test
    void testRead() {
        sensorRepository.save(sample);

        Sensor actual = get(sample.getSensorNo());
        log.debug("read actual: {}", actual);
        equals(sample, actual);
    }

    @DisplayName("Sensor JPA: 수정 테스트")
    @Test
    void testUpdate() {
        String location = "클래스 A";
        String spot = "후문";

        sensorRepository.save(sample);

        Sensor testUpdate = get(sample.getSensorNo());
        testUpdate.updateSensorPosition(location, spot);
        sensorRepository.flush();

        Sensor actual = get(sample.getSensorNo());
        log.debug("update actual: {}", actual);
        equals(testUpdate, actual);
    }

    @DisplayName("Sensor JPA: 삭제 테스트")
    @Test
    void testDelete() {
        sensorRepository.save(sample);

        Sensor delete = get(sample.getSensorNo());
        sensorRepository.deleteById(delete.getSensorNo());
        log.debug("delete actual: {}", delete);

        Optional<Sensor> actual = sensorRepository.findById(sample.getSensorNo());
        Assertions.assertTrue(actual.isEmpty());
    }

    private Sensor get(Integer sensorNo) {
        Optional<Sensor> optional = sensorRepository.findById(sensorNo);
        Assertions.assertTrue(optional.isPresent());
        return optional.get();
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
