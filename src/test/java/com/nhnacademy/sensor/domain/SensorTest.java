package com.nhnacademy.sensor.domain;

import com.nhnacademy.CustomDataJpaTest;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@CustomDataJpaTest
class SensorTest {

    private static final String TEST_GATEWAY_ID = "test-gateway-id";

    private static final String TEST_SENSOR_ID = "test-sensor-id";

    private static final String TEST_SENSOR_LOCATION = "test-sensor-location";

    private static final String TEST_SENSOR_SPOT = "test-sensor-spot";

    @Autowired
    private EntityManager em;

    @DisplayName("생성자 테스트: 파라미터 주입 테스트 1")
    @Test
    void testStaticConstructor1() {
        Sensor sensor = Sensor.ofNewSensor(TEST_GATEWAY_ID, TEST_SENSOR_ID);
        Assertions.assertAll(
                () -> Assertions.assertEquals(TEST_GATEWAY_ID, sensor.getGatewayId()),
                () -> Assertions.assertEquals(TEST_SENSOR_ID, sensor.getSensorId()),
                () -> Assertions.assertNull(sensor.getSensorLocation()),
                () -> Assertions.assertNull(sensor.getSensorSpot())
        );
    }

    @DisplayName("생성자 테스트: 파라미터 주입 테스트 2")
    @Test
    void testStaticConstructor2() {
        Sensor sensor = Sensor.ofNewSensor(
                TEST_GATEWAY_ID, TEST_SENSOR_ID,
                TEST_SENSOR_LOCATION, TEST_SENSOR_SPOT
        );
        Assertions.assertAll(
                () -> Assertions.assertEquals(TEST_GATEWAY_ID, sensor.getGatewayId()),
                () -> Assertions.assertEquals(TEST_SENSOR_ID, sensor.getSensorId()),
                () -> Assertions.assertEquals(TEST_SENSOR_LOCATION, sensor.getSensorLocation()),
                () -> Assertions.assertEquals(TEST_SENSOR_SPOT, sensor.getSensorSpot())
        );
    }

    @DisplayName("Entity: 삽입 테스트")
    @Test
    void testCreate() {
        Sensor testSave = Sensor.ofNewSensor(TEST_GATEWAY_ID, TEST_SENSOR_ID);
        em.persist(testSave);

        log.debug("create entity: {}", testSave);
        Assertions.assertNotNull(em.find(Sensor.class, testSave.getSensorNo()));
    }

    @DisplayName("Entity: 조회 테스트")
    @Test
    void testRead() {
        Sensor testRead = Sensor.ofNewSensor(TEST_GATEWAY_ID, TEST_SENSOR_ID);
        em.persist(testRead);

        Sensor actual = em.find(Sensor.class, testRead.getSensorNo());
        log.debug("find read entity: {}", actual);

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(TEST_GATEWAY_ID, actual.getGatewayId()),
                () -> Assertions.assertEquals(TEST_SENSOR_ID, actual.getSensorId()),
                () -> Assertions.assertNull(actual.getSensorLocation()),
                () -> Assertions.assertNull(actual.getSensorSpot())
        );
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String location = "클래스 B";
        String spot = "책상 1번";

        Sensor testUpdate = Sensor.ofNewSensor(TEST_GATEWAY_ID, TEST_SENSOR_ID);
        em.persist(testUpdate);

        testUpdate.updateSensorPosition(location, spot);
        em.flush();
        em.clear();

        Sensor actual = em.find(Sensor.class, testUpdate.getSensorNo());
        log.debug("find update entity: {}", actual);

        Assertions.assertAll(
                () -> Assertions.assertEquals(TEST_GATEWAY_ID, actual.getGatewayId()),
                () -> Assertions.assertEquals(TEST_SENSOR_ID, actual.getSensorId()),
                () -> Assertions.assertEquals(location, actual.getSensorLocation()),
                () -> Assertions.assertEquals(spot, actual.getSensorSpot())
        );
    }

    @DisplayName("Entity: 삭제 테스트")
    @Test
    void testDelete() {
        Sensor testDelete = Sensor.ofNewSensor(TEST_GATEWAY_ID, TEST_SENSOR_ID);
        em.persist(testDelete);
        log.debug("delete entity: {}", testDelete);

        Assertions.assertNotNull(em.find(Sensor.class, testDelete.getSensorNo()));
        em.remove(testDelete);
        em.flush();
        em.clear();

        Sensor actual = em.find(Sensor.class, testDelete.getSensorNo());
        Assertions.assertNull(actual);
    }
}
