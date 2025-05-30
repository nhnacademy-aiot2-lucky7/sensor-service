package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.repository.DataTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@CustomDataJpaTest
class CustomSensorDataMappingRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private SensorDataMappingRepository sensorDataMappingRepository;

    private List<Sensor> sensors;

    private List<DataType> dataTypes;

    @BeforeEach
    void setUp() {
        sensors = testSensors();
        sensorRepository.saveAll(sensors);

        dataTypes = testDataTypes();
        dataTypeRepository.saveAll(dataTypes);

        for (int n = 0; n < 2; n++) {
            for (int m = n; m < n + 2; m++) {
                SensorDataMapping sensorDataMapping =
                        SensorDataMapping.ofNewSensorDataType(
                                sensors.get(n),
                                dataTypes.get(m),
                                ((n & 1) == 1) ? SensorStatus.COMPLETED : SensorStatus.ABANDONED
                        );
                sensorDataMappingRepository.save(sensorDataMapping);
            }
        }
    }

    @DisplayName("Test Data: 테스트 데이터 양식 검증")
    @Test
    void testData() {
        List<Sensor> findSensors = sensorRepository.findAll();
        List<DataType> findDataTypes = dataTypeRepository.findAll();
        List<SensorDataMapping> finaSensorDataMappings = sensorDataMappingRepository.findAll();

        Assertions.assertAll(
                () -> Assertions.assertEquals(sensors.size(), findSensors.size()),
                () -> Assertions.assertEquals(dataTypes.size(), findDataTypes.size()),
                () -> Assertions.assertEquals(4, finaSensorDataMappings.size())
        );
    }

    @DisplayName("QueryDSL: 존재하는 센서 매핑 Entity 조회 시도")
    @Test
    void testFindByGatewayIdAndSensorIdAndDataTypeEnName_save() {
        List<SensorDataMapping> expects = sensorDataMappingRepository.findAll();

        sensors.forEach(sensor -> {
            List<SensorDataMapping> sensorDataMappings = new ArrayList<>();

            // 조건에 해당하는 데이터들을 검색
            dataTypes.forEach(dataType -> {
                SensorDataMapping sensorDataMapping =
                        sensorDataMappingRepository
                                .findByGatewayIdAndSensorIdAndDataTypeEnName(
                                        sensor.getGatewayId(),
                                        sensor.getSensorId(),
                                        dataType.getDataTypeEnName()
                                );
                if (sensorDataMapping != null) {
                    sensorDataMappings.add(sensorDataMapping);
                }
            });
            Assertions.assertEquals(2, sensorDataMappings.size());

            // 실제 데이터와 동일한지 검증
            AtomicInteger count = new AtomicInteger();
            sensorDataMappings.forEach(actual ->
                    expects.forEach(expected -> {
                        if (Objects.equals(expected.getSensorDataNo(), actual.getSensorDataNo())) {
                            equals(expected, actual);
                            count.incrementAndGet();
                        }
                    })
            );
            Assertions.assertEquals(sensorDataMappings.size(), count.get());
        });
    }

    @DisplayName("QueryDSL: 존재하지 않는 센서 매핑 Entity 조회 시도")
    @Test
    void testFindByGatewayIdAndSensorIdAndDataTypeEnName_notSave() {
        List<SensorDataMapping> sensorDataMappings = new ArrayList<>();

        // 조건에 해당하는 데이터들을 검색
        dataTypes.forEach(dataType -> {
            SensorDataMapping sensorDataMapping =
                    sensorDataMappingRepository
                            .findByGatewayIdAndSensorIdAndDataTypeEnName(
                                    Long.MAX_VALUE,
                                    "test-sensor-id",
                                    dataType.getDataTypeEnName()
                            );
            if (sensorDataMapping != null) {
                sensorDataMappings.add(sensorDataMapping);
            }
        });
        Assertions.assertEquals(0, sensorDataMappings.size());
    }

    /*@DisplayName("QueryDSL: 존재하는 센서 매핑 DTO 조회 시도")
    @Test
    void testFindMappingInfoBySensorId_save() {
        List<SensorDataMapping> expects = sensorDataMappingRepository.findAll();

        sensors.forEach(sensor -> {

            // 조건에 해당하는 데이터들을 검색
            List<SensorDataMappingFrontResponse> sensorDataMappingFrontRespons =
                    sensorDataMappingRepository.findMappingInfoBySensorId(sensor.getSensorId());
            Assertions.assertEquals(2, sensorDataMappingFrontRespons.size());

            // 실제 데이터와 동일한지 검증
            sensorDataMappingFrontRespons.forEach(sensorDataMappingFrontResponse -> {
                SensorDataMapping expected =
                        sensorDataMappingRepository.findByGatewayIdAndSensorIdAndDataTypeEnName(
                                sensorDataMappingFrontResponse.getGatewayId(),
                                sensorDataMappingFrontResponse.getSensorId(),
                                sensorDataMappingFrontResponse.getDataTypeEnName()
                        );
                Assertions.assertNotNull(expected);

                Sensor expectedSensor = expected.getSensor();
                DataType expectedDataType = expected.getDataType();
                Assertions.assertAll(
                        () -> Assertions.assertEquals(expectedSensor.getGatewayId(), sensorDataMappingFrontResponse.getGatewayId()),
                        () -> Assertions.assertEquals(expectedSensor.getSensorId(), sensorDataMappingFrontResponse.getSensorId()),
                        () -> Assertions.assertEquals(expectedSensor.getSensorLocation(), sensorDataMappingFrontResponse.getSensorLocation()),
                        () -> Assertions.assertEquals(expectedSensor.getSensorSpot(), sensorDataMappingFrontResponse.getSensorSpot()),

                        () -> Assertions.assertEquals(expectedDataType.getDataTypeEnName(), sensorDataMappingFrontResponse.getDataTypeEnName()),
                        () -> Assertions.assertEquals(expectedDataType.getDataTypeKrName(), sensorDataMappingFrontResponse.getDataTypeKrName()),

                        () -> Assertions.assertEquals(expected.getSensorStatus(), sensorDataMappingFrontResponse.getSensorStatus())
                );
            });
        });
    }

    @DisplayName("QueryDSL: 존재하지 않는 센서 매핑 DTO 조회 시도")
    @Test
    void testFindMappingInfoBySensorId_notSave() {
        List<SensorDataMappingFrontResponse> sensorDataMappingFrontRespons =
                sensorDataMappingRepository.findMappingInfoBySensorId("test-sensor-id");
        Assertions.assertEquals(0, sensorDataMappingFrontRespons.size());
    }*/

    private List<Sensor> testSensors() {
        return List.of(
                Sensor.ofNewSensor(1L, "24e124126c152919", "클래스 A", "후문"),
                Sensor.ofNewSensor(2L, "24e124700d084198", "클래스 B", "책상 라인 1번")
        );
    }

    private List<DataType> testDataTypes() {
        return List.of(
                DataType.ofNewDataType("temperature", "온도"),
                DataType.ofNewDataType("humidity", "습도"),
                DataType.ofNewDataType("co2", "이산화탄소")
        );
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
