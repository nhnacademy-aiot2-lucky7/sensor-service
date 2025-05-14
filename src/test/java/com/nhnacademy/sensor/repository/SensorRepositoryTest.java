package com.nhnacademy.sensor.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.SensorTestingData;
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

    @Autowired
    private SensorRepository sensorRepository;

    private Sensor test;

    @BeforeEach
    void setUp() {
        test = Sensor.ofNewSensor(
                SensorTestingData.TEST_GATEWAY_ID,
                SensorTestingData.TEST_SENSOR_ID,
                SensorTestingData.TEST_SENSOR_LOCATION,
                SensorTestingData.TEST_SENSOR_SPOT
        );
    }

    @DisplayName("JPA: 삽입 테스트")
    @Test
    void testCreate() {
        Sensor sensor = sensorRepository.save(test);
        log.debug("create actual: {}", sensor);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(sensor),
                () -> Assertions.assertTrue(sensorRepository.findById(test.getSensorNo()).isPresent())
        );
    }

    @DisplayName("JPA: 조회 테스트")
    @Test
    void testRead() {
        sensorRepository.save(test);

        Sensor actual = get(test.getSensorNo());
        log.debug("read actual: {}", actual);
        equals(test, actual);
    }

    @DisplayName("JPA: 수정 테스트")
    @Test
    void testUpdate() {
        String location = "클래스 A";
        String spot = "후문";

        sensorRepository.save(test);

        Sensor testUpdate = get(test.getSensorNo());
        testUpdate.updateSensorPosition(location, spot);
        sensorRepository.flush();

        Sensor actual = get(test.getSensorNo());
        log.debug("update actual: {}", actual);
        equals(testUpdate, actual);
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

    @DisplayName("JPA: 조회 테스트(gatewayId & sensorId)")
    @Test
    void testFindByGatewayIdAndSensorId() {
        sensorRepository.save(test);

        Sensor actual = sensorRepository.findByGatewayIdAndSensorId(
                test.getGatewayId(), test.getSensorId()
        );
        log.debug("find actual: {}", actual);
        equals(test, actual);
    }

    @DisplayName("JPA: 존재 테스트(gatewayId & sensorId)")
    @Test
    void testExistsByGatewayIdAndSensorId() {
        sensorRepository.save(test);

        Assertions.assertTrue(
                sensorRepository.existsByGatewayIdAndSensorId(
                        test.getGatewayId(), test.getSensorId()
                )
        );
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
