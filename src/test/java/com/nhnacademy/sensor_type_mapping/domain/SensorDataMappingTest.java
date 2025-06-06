package com.nhnacademy.sensor_type_mapping.domain;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.type.DataTypeTestingData;
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
class SensorDataMappingTest {

    private final long testGatewayId = SensorTestingData.TEST_GATEWAY_ID;

    private final String testSensorId = SensorTestingData.TEST_SENSOR_ID;

    private final String testEnName = DataTypeTestingData.TEST_EN_NAME;

    private final String testKrName = DataTypeTestingData.TEST_KR_NAME;

    private static final SensorStatus DEFAULT_STATUS = SensorStatus.PENDING;

    @Autowired
    private EntityManager em;

    private Sensor sensor;

    private DataType dataType;

    @BeforeEach
    void setUp() {
        sensor = Sensor.ofNewSensor(
                testGatewayId,
                testSensorId,
                SensorTestingData.TEST_SENSOR_NAME,
                SensorTestingData.TEST_SENSOR_LOCATION,
                SensorTestingData.TEST_SENSOR_SPOT
        );
        em.persist(sensor);

        dataType = DataType.ofNewDataType(
                testEnName,
                testKrName
        );
        em.persist(dataType);
    }

    @DisplayName("생성자 테스트: 파라미터 주입 테스트")
    @Test
    void testStaticConstructor() {
        SensorDataMapping sensorDataMapping = SensorDataMapping.ofNewSensorDataType(sensor, dataType, DEFAULT_STATUS);

        Assertions.assertAll(
                () -> Assertions.assertEquals(testGatewayId, sensorDataMapping.getSensor().getGatewayId()),
                () -> Assertions.assertEquals(testSensorId, sensorDataMapping.getSensor().getSensorId()),

                () -> Assertions.assertEquals(testEnName, sensorDataMapping.getDataType().getDataTypeEnName()),
                () -> Assertions.assertEquals(testKrName, sensorDataMapping.getDataType().getDataTypeKrName()),

                () -> Assertions.assertEquals(DEFAULT_STATUS, sensorDataMapping.getSensorStatus())
        );
    }

    @DisplayName("Entity: 삽입 테스트")
    @Test
    void testCreate() {
        SensorDataMapping testSave = SensorDataMapping.ofNewSensorDataType(sensor, dataType, DEFAULT_STATUS);
        em.persist(testSave);

        log.debug("create entity: {}", testSave);
        Assertions.assertNotNull(em.find(SensorDataMapping.class, testSave.getSensorDataNo()));
    }

    @DisplayName("Entity: 조회 테스트")
    @Test
    void testRead() {
        SensorDataMapping testRead = SensorDataMapping.ofNewSensorDataType(sensor, dataType, DEFAULT_STATUS);
        em.persist(testRead);

        SensorDataMapping actual = em.find(SensorDataMapping.class, testRead.getSensorDataNo());
        log.debug("find read entity: {}", actual);
        equals(testRead, actual);
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String location = "클래스 B";
        String spot = "책상 라인 1번";
        String krName = "온도";
        SensorStatus sensorStatus = SensorStatus.ABANDONED;

        // 최초 할당
        SensorDataMapping testUpdate = SensorDataMapping.ofNewSensorDataType(sensor, dataType, DEFAULT_STATUS);
        em.persist(testUpdate);

        // 센서 위치 정보 수정
        sensor.updateSensorPosition(location, spot);

        // 데이터 타입 정보 수정
        dataType.updateTypeKrName("온도");

        // 센서 상태 수정
        testUpdate.updateStatus(sensorStatus);
        em.flush();
        em.clear();

        SensorDataMapping actual = em.find(SensorDataMapping.class, testUpdate.getSensorDataNo());
        log.debug("find update entity: {}", actual);

        equals(testUpdate, actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(location, actual.getSensor().getSensorLocation()),
                () -> Assertions.assertEquals(spot, actual.getSensor().getSensorSpot()),

                () -> Assertions.assertEquals(dataType.getDataTypeEnName(), actual.getDataType().getDataTypeEnName()),
                () -> Assertions.assertEquals(krName, actual.getDataType().getDataTypeKrName()),

                () -> Assertions.assertEquals(sensorStatus, actual.getSensorStatus())
        );
    }

    @DisplayName("Entity: 삭제 테스트")
    @Test
    void testDelete() {
        SensorDataMapping testDelete = SensorDataMapping.ofNewSensorDataType(sensor, dataType, DEFAULT_STATUS);
        em.persist(testDelete);
        log.debug("delete entity: {}", testDelete);

        Assertions.assertNotNull(em.find(SensorDataMapping.class, testDelete.getSensorDataNo()));
        em.remove(testDelete);
        em.flush();
        em.clear();

        SensorDataMapping actual = em.find(SensorDataMapping.class, testDelete.getSensorDataNo());
        Assertions.assertNull(actual);
    }

    private void equals(SensorDataMapping expected, SensorDataMapping actual) {
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
