package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor_type_mapping.domain.SensorMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.repository.DataTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
@CustomDataJpaTest
class SensorMappingRepositoryTest {

    private static final String TEST_GATEWAY_ID = "test-gateway-id";

    private static final String TEST_SENSOR_ID = "test-sensor-id";

    private static final String TEST_SENSOR_LOCATION = "test-sensor-location";

    private static final String TEST_SENSOR_SPOT = "test-sensor-spot";

    private static final String TEST_EN_NAME = "test-en-name";

    private static final String TEST_KR_NAME = "test-kr-name";

    private static final SensorStatus DEFAULT_STATUS = SensorStatus.PENDING;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private SensorMappingRepository sensorMappingRepository;

    private Sensor sensor;

    private DataType dataType;

    private SensorMapping test;

    @BeforeEach
    void setUp() {
        sensor = Sensor.ofNewSensor(
                TEST_GATEWAY_ID, TEST_SENSOR_ID,
                TEST_SENSOR_LOCATION, TEST_SENSOR_SPOT
        );
        sensorRepository.save(sensor);

        dataType = DataType.ofNewDataType(
                TEST_EN_NAME,
                TEST_KR_NAME
        );
        dataTypeRepository.save(dataType);

        test = SensorMapping.ofNewSensorDataType(sensor, dataType, DEFAULT_STATUS);
    }

    @DisplayName("JPA: 삽입 테스트")
    @Test
    void testCreate() {
        sensorMappingRepository.save(test);

        log.debug("create actual: {}", test);
        Assertions.assertTrue(sensorMappingRepository.findById(test.getSensorDataNo()).isPresent());
    }

    @DisplayName("JPA: 조회 테스트")
    @Test
    void testRead() {
        sensorMappingRepository.save(test);

        SensorMapping actual = get(test.getSensorDataNo());
        log.debug("read actual: {}", actual);
        equals(test, actual);
    }

    @DisplayName("JPA: 수정 테스트")
    @Test
    void testUpdate() {
        String location = "클래스 A";
        String spot = "단상 위";
        String krName = "습도";
        SensorStatus sensorStatus = SensorStatus.COMPLETED;

        // 최초 할당
        sensorMappingRepository.save(test);

        // 센서 위치 정보 수정
        sensor.updateSensorPosition(location, spot);

        // 데이터 타입 정보 수정
        dataType.updateTypeKrName(krName);

        // 센서 상태 수정
        test.updateStatus(sensorStatus);
        sensorMappingRepository.flush();

        SensorMapping actual = get(test.getSensorDataNo());
        log.debug("update actual: {}", actual);

        equals(test, actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(location, actual.getSensor().getSensorLocation()),
                () -> Assertions.assertEquals(spot, actual.getSensor().getSensorSpot()),

                () -> Assertions.assertEquals(dataType.getDataTypeEnName(), actual.getDataType().getDataTypeEnName()),
                () -> Assertions.assertEquals(krName, actual.getDataType().getDataTypeKrName()),

                () -> Assertions.assertEquals(sensorStatus, actual.getSensorStatus())
        );
    }

    @DisplayName("JPA: 삭제 테스트")
    @Test
    void testDelete() {
        sensorMappingRepository.save(test);

        SensorMapping delete = get(test.getSensorDataNo());
        sensorMappingRepository.deleteById(delete.getSensorDataNo());
        log.debug("delete actual: {}", delete);

        Optional<SensorMapping> actual = sensorMappingRepository.findById(test.getSensorDataNo());
        Assertions.assertTrue(actual.isEmpty());

        Assertions.assertAll(
                () -> Assertions.assertTrue(sensorRepository.existsBySensorId(TEST_SENSOR_ID)),
                () -> Assertions.assertTrue(dataTypeRepository.existsById(TEST_EN_NAME))
        );
    }

    private SensorMapping get(Long sensorDataNo) {
        Optional<SensorMapping> optional = sensorMappingRepository.findById(sensorDataNo);
        Assertions.assertTrue(optional.isPresent());
        return optional.get();
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
