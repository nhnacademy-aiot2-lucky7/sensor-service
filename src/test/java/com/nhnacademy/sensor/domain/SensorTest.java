package com.nhnacademy.sensor.domain;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.SensorTestingData;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@CustomDataJpaTest
class SensorTest {

    private final String testGatewayId = SensorTestingData.TEST_GATEWAY_ID;

    private final String testSensorId = SensorTestingData.TEST_SENSOR_ID;

    private final String testSensorLocation = SensorTestingData.TEST_SENSOR_LOCATION;

    private final String testSensorSpot = SensorTestingData.TEST_SENSOR_SPOT;

    @Autowired
    private EntityManager em;

    @DisplayName("생성자 테스트: 파라미터 주입 테스트")
    @Test
    void testStaticConstructor() {
        Sensor sensor = Sensor.ofNewSensor(
                testGatewayId, testSensorId,
                testSensorLocation, testSensorSpot
        );
        Assertions.assertAll(
                () -> Assertions.assertEquals(testGatewayId, sensor.getGatewayId()),
                () -> Assertions.assertEquals(testSensorId, sensor.getSensorId()),
                () -> Assertions.assertEquals(testSensorLocation, sensor.getSensorLocation()),
                () -> Assertions.assertEquals(testSensorSpot, sensor.getSensorSpot())
        );
    }

    @DisplayName("Entity: 삽입 테스트")
    @Test
    void testCreate() {
        Sensor testSave = Sensor.ofNewSensor(
                testGatewayId, testSensorId,
                testSensorLocation, testSensorSpot
        );
        em.persist(testSave);

        log.debug("create entity: {}", testSave);
        Assertions.assertNotNull(em.find(Sensor.class, testSave.getSensorNo()));
    }

    @DisplayName("Entity: 조회 테스트")
    @Test
    void testRead() {
        Sensor testRead = Sensor.ofNewSensor(
                testGatewayId, testSensorId,
                testSensorLocation, testSensorSpot
        );
        em.persist(testRead);

        Sensor actual = em.find(Sensor.class, testRead.getSensorNo());
        log.debug("find read entity: {}", actual);
        equals(testRead, actual);
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String location = "클래스 B";
        String spot = "책상 1번";

        Sensor testUpdate = Sensor.ofNewSensor(
                testGatewayId, testSensorId,
                testSensorLocation, testSensorSpot
        );
        em.persist(testUpdate);

        testUpdate.updateSensorPosition(location, spot);
        em.flush();
        em.clear();

        Sensor actual = em.find(Sensor.class, testUpdate.getSensorNo());
        log.debug("find update entity: {}", actual);
        equals(testUpdate, actual);
    }

    @DisplayName("Entity: 삭제 테스트")
    @Test
    void testDelete() {
        Sensor testDelete = Sensor.ofNewSensor(
                testGatewayId, testSensorId,
                testSensorLocation, testSensorSpot
        );
        em.persist(testDelete);
        log.debug("delete entity: {}", testDelete);

        Assertions.assertNotNull(em.find(Sensor.class, testDelete.getSensorNo()));
        em.remove(testDelete);
        em.flush();
        em.clear();

        Sensor actual = em.find(Sensor.class, testDelete.getSensorNo());
        Assertions.assertNull(actual);
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
