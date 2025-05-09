package com.nhnacademy.sensor_type_mapping.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor_type_mapping.domain.SensorMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.sensor_type_mapping.dto.SensorMappingFrontResponse;
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
class CustomSensorMappingRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private SensorMappingRepository sensorMappingRepository;

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
                SensorMapping sensorMapping =
                        SensorMapping.ofNewSensorDataType(
                                sensors.get(n),
                                dataTypes.get(m),
                                ((n & 1) == 1) ? SensorStatus.COMPLETED : SensorStatus.ABANDONED
                        );
                sensorMappingRepository.save(sensorMapping);
            }
        }
    }

    @DisplayName("Test Data: 테스트 데이터 양식 검증")
    @Test
    void testData() {
        List<Sensor> findSensors = sensorRepository.findAll();
        List<DataType> findDataTypes = dataTypeRepository.findAll();
        List<SensorMapping> finaSensorMappings = sensorMappingRepository.findAll();

        Assertions.assertAll(
                () -> Assertions.assertEquals(sensors.size(), findSensors.size()),
                () -> Assertions.assertEquals(dataTypes.size(), findDataTypes.size()),
                () -> Assertions.assertEquals(4, finaSensorMappings.size())
        );
    }

    @DisplayName("QueryDSL: 존재하는 센서 매핑 Entity 조회 시도")
    @Test
    void testFindByGatewayIdAndSensorIdAndDataTypeEnName_save() {
        List<SensorMapping> expects = sensorMappingRepository.findAll();

        sensors.forEach(sensor -> {
            List<SensorMapping> sensorMappings = new ArrayList<>();

            // 조건에 해당하는 데이터들을 검색
            dataTypes.forEach(dataType -> {
                SensorMapping sensorMapping =
                        sensorMappingRepository
                                .findByGatewayIdAndSensorIdAndDataTypeEnName(
                                        sensor.getGatewayId(),
                                        sensor.getSensorId(),
                                        dataType.getDataTypeEnName()
                                );
                if (sensorMapping != null) {
                    sensorMappings.add(sensorMapping);
                }
            });
            Assertions.assertEquals(2, sensorMappings.size());

            // 실제 데이터와 동일한지 검증
            AtomicInteger count = new AtomicInteger();
            sensorMappings.forEach(actual ->
                    expects.forEach(expected -> {
                        if (Objects.equals(expected.getSensorDataNo(), actual.getSensorDataNo())) {
                            equals(expected, actual);
                            count.incrementAndGet();
                        }
                    })
            );
            Assertions.assertEquals(sensorMappings.size(), count.get());
        });
    }

    @DisplayName("QueryDSL: 존재하지 않는 센서 매핑 Entity 조회 시도")
    @Test
    void testFindByGatewayIdAndSensorIdAndDataTypeEnName_notSave() {
        List<SensorMapping> sensorMappings = new ArrayList<>();

        // 조건에 해당하는 데이터들을 검색
        dataTypes.forEach(dataType -> {
            SensorMapping sensorMapping =
                    sensorMappingRepository
                            .findByGatewayIdAndSensorIdAndDataTypeEnName(
                                    "test-gateway-C",
                                    "test-sensor-id",
                                    dataType.getDataTypeEnName()
                            );
            if (sensorMapping != null) {
                sensorMappings.add(sensorMapping);
            }
        });
        Assertions.assertEquals(0, sensorMappings.size());
    }

    @DisplayName("QueryDSL: 존재하는 센서 매핑 DTO 조회 시도")
    @Test
    void testFindMappingInfoBySensorId_save() {
        List<SensorMapping> expects = sensorMappingRepository.findAll();

        sensors.forEach(sensor -> {

            // 조건에 해당하는 데이터들을 검색
            List<SensorMappingFrontResponse> sensorMappingFrontResponses =
                    sensorMappingRepository.findMappingInfoBySensorId(sensor.getSensorId());
            Assertions.assertEquals(2, sensorMappingFrontResponses.size());

            // 실제 데이터와 동일한지 검증
            sensorMappingFrontResponses.forEach(sensorMappingFrontResponse -> {
                SensorMapping expected =
                        sensorMappingRepository.findByGatewayIdAndSensorIdAndDataTypeEnName(
                                sensorMappingFrontResponse.getGatewayId(),
                                sensorMappingFrontResponse.getSensorId(),
                                sensorMappingFrontResponse.getDataTypeEnName()
                        );
                Assertions.assertNotNull(expected);

                Sensor expectedSensor = expected.getSensor();
                DataType expectedDataType = expected.getDataType();
                Assertions.assertAll(
                        () -> Assertions.assertEquals(expectedSensor.getGatewayId(), sensorMappingFrontResponse.getGatewayId()),
                        () -> Assertions.assertEquals(expectedSensor.getSensorId(), sensorMappingFrontResponse.getSensorId()),
                        () -> Assertions.assertEquals(expectedSensor.getSensorLocation(), sensorMappingFrontResponse.getSensorLocation()),
                        () -> Assertions.assertEquals(expectedSensor.getSensorSpot(), sensorMappingFrontResponse.getSensorSpot()),

                        () -> Assertions.assertEquals(expectedDataType.getDataTypeEnName(), sensorMappingFrontResponse.getDataTypeEnName()),
                        () -> Assertions.assertEquals(expectedDataType.getDataTypeKrName(), sensorMappingFrontResponse.getDataTypeKrName()),

                        () -> Assertions.assertEquals(expected.getSensorStatus(), sensorMappingFrontResponse.getSensorStatus())
                );
            });
        });
    }

    @DisplayName("QueryDSL: 존재하지 않는 센서 매핑 DTO 조회 시도")
    @Test
    void testFindMappingInfoBySensorId_notSave() {
        List<SensorMappingFrontResponse> sensorMappingFrontResponses =
                sensorMappingRepository.findMappingInfoBySensorId("test-sensor-id");
        Assertions.assertEquals(0, sensorMappingFrontResponses.size());
    }

    private List<Sensor> testSensors() {
        return List.of(
                Sensor.ofNewSensor("test-gateway-A", "24e124126c152919", "클래스 A", "후문"),
                Sensor.ofNewSensor("test-gateway-B", "24e124700d084198", "클래스 B", "책상 라인 1번")
        );
    }

    private List<DataType> testDataTypes() {
        return List.of(
                DataType.ofNewDataType("temperature", "온도"),
                DataType.ofNewDataType("humidity", "습도"),
                DataType.ofNewDataType("co2", "이산화탄소")
        );
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
