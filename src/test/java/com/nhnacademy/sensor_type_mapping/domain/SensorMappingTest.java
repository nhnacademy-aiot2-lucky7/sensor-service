package com.nhnacademy.sensor_type_mapping.domain;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.type.domain.DataType;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@CustomDataJpaTest
class SensorMappingTest {

    private static final String TEST_GATEWAY_ID = "test-gateway-id";

    private static final String TEST_SENSOR_ID = "test-sensor-id";

    private static final String TEST_EN_NAME = "test-en-name";

    private static final String TEST_KR_NAME = "test-kr-name";

    @Autowired
    private EntityManager em;

    private Sensor sensor;

    private DataType dataType;

    @BeforeEach
    void setUp() {
        sensor = Sensor.ofNewSensor(
                TEST_GATEWAY_ID,
                TEST_SENSOR_ID
        );
        em.persist(sensor);

        dataType = DataType.ofNewDataType(
                TEST_EN_NAME,
                TEST_KR_NAME
        );
        em.persist(dataType);
    }

    @DisplayName("생성자 테스트: 파라미터 주입 테스트 1")
    @Test
    void testStaticConstructor1() {
        SensorMapping sensorMapping = SensorMapping.ofNewSensorDataType(sensor, dataType);

        Assertions.assertAll(
                () -> Assertions.assertEquals(TEST_GATEWAY_ID, sensorMapping.getSensor().getGatewayId()),
                () -> Assertions.assertEquals(TEST_SENSOR_ID, sensorMapping.getSensor().getSensorId()),

                () -> Assertions.assertEquals(TEST_EN_NAME, sensorMapping.getDataType().getDataTypeEnName()),
                () -> Assertions.assertEquals(TEST_KR_NAME, sensorMapping.getDataType().getDataTypeKrName()),

                () -> Assertions.assertEquals(SensorStatus.PENDING, sensorMapping.getSensorStatus())
        );
    }

    @DisplayName("생성자 테스트: 파라미터 주입 테스트 2")
    @Test
    void testStaticConstructor2() {
        SensorStatus sensorStatus = SensorStatus.COMPLETED;

        SensorMapping sensorMapping = SensorMapping.ofNewSensorDataType(sensor, dataType, sensorStatus);

        Assertions.assertAll(
                () -> Assertions.assertEquals(TEST_GATEWAY_ID, sensorMapping.getSensor().getGatewayId()),
                () -> Assertions.assertEquals(TEST_SENSOR_ID, sensorMapping.getSensor().getSensorId()),

                () -> Assertions.assertEquals(TEST_EN_NAME, sensorMapping.getDataType().getDataTypeEnName()),
                () -> Assertions.assertEquals(TEST_KR_NAME, sensorMapping.getDataType().getDataTypeKrName()),

                () -> Assertions.assertEquals(sensorStatus, sensorMapping.getSensorStatus())
        );
    }

    @DisplayName("Entity: 삽입 테스트")
    @Test
    void testCreate() {
        SensorMapping testSave = SensorMapping.ofNewSensorDataType(sensor, dataType);
        em.persist(testSave);

        log.debug("create entity: {}", testSave);
        Assertions.assertNotNull(em.find(SensorMapping.class, testSave.getSensorDataNo()));
    }

    @DisplayName("Entity: 조회 테스트")
    @Test
    void testRead() {
        SensorMapping testRead = SensorMapping.ofNewSensorDataType(sensor, dataType);
        em.persist(testRead);

        SensorMapping actual = em.find(SensorMapping.class, testRead.getSensorDataNo());
        log.debug("find read entity: {}", actual);

        equals(testRead, actual);
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String location = "클래스 B";
        String spot = "책상 라인 1번";
        String enName = "temperature";
        String krName = "온도";
        SensorStatus sensorStatus = SensorStatus.ABANDONED;

        // 최초 할당
        SensorMapping testUpdate = SensorMapping.ofNewSensorDataType(sensor, dataType);
        em.persist(testUpdate);

        // 새로운 데이터 타입 할당
        DataType newDataType = DataType.ofNewDataType(enName, krName);
        em.persist(newDataType);
        em.flush();

        // 센서 위치 정보 수정
        sensor.updateSensorPosition(location, spot);

        // 데이터 타입 및 상태 수정
        testUpdate.updateDataType(newDataType);
        testUpdate.updateStatus(sensorStatus);
        em.flush();
        em.clear();

        SensorMapping actual = em.find(SensorMapping.class, testUpdate.getSensorDataNo());
        log.debug("find update entity: {}", actual);

        equals(testUpdate, actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(location, actual.getSensor().getSensorLocation()),
                () -> Assertions.assertEquals(spot, actual.getSensor().getSensorSpot()),

                () -> Assertions.assertEquals(enName, actual.getDataType().getDataTypeEnName()),
                () -> Assertions.assertEquals(krName, actual.getDataType().getDataTypeKrName()),

                () -> Assertions.assertEquals(sensorStatus, actual.getSensorStatus())
        );
    }

    @DisplayName("Entity: 삭제 테스트")
    @Test
    void testDelete() {
        SensorMapping testDelete = SensorMapping.ofNewSensorDataType(sensor, dataType);
        em.persist(testDelete);
        log.debug("delete entity: {}", testDelete);

        Assertions.assertNotNull(em.find(SensorMapping.class, testDelete.getSensorDataNo()));
        em.remove(testDelete);
        em.flush();
        em.clear();

        SensorMapping actual = em.find(SensorMapping.class, testDelete.getSensorDataNo());
        Assertions.assertNull(actual);
    }

    private void equals(SensorMapping expected, SensorMapping actual) {
        Sensor expectedSensor = expected.getSensor();
        DataType expectedDataType = expected.getDataType();

        Sensor actualSensor = actual.getSensor();
        DataType actualDataType = actual.getDataType();

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedSensor.getSensorNo(), actualSensor.getSensorNo()),
                () -> Assertions.assertEquals(expectedSensor.getGatewayId(), actualSensor.getGatewayId()),
                () -> Assertions.assertEquals(expectedSensor.getSensorId(), actualSensor.getSensorId()),
                () -> Assertions.assertEquals(expectedSensor.getSensorLocation(), actualSensor.getSensorLocation()),
                () -> Assertions.assertEquals(expectedSensor.getSensorSpot(), actualSensor.getSensorSpot()),

                () -> Assertions.assertEquals(expectedDataType.getDataTypeEnName(), actualDataType.getDataTypeEnName()),
                () -> Assertions.assertEquals(expectedDataType.getDataTypeKrName(), actualDataType.getDataTypeKrName()),

                () -> Assertions.assertEquals(expected.getSensorStatus(), actual.getSensorStatus())
        );
    }
}
