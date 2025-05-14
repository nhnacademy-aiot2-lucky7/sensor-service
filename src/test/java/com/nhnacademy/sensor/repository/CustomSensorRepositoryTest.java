package com.nhnacademy.sensor.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@CustomDataJpaTest
class CustomSensorRepositoryTest {

    private final String testGatewayId = SensorTestingData.TEST_GATEWAY_ID;

    private final String testSensorId = SensorTestingData.TEST_SENSOR_ID;

    private final String testSensorLocation = SensorTestingData.TEST_SENSOR_LOCATION;

    private final String testSensorSpot = SensorTestingData.TEST_SENSOR_SPOT;

    @Autowired
    private SensorRepository sensorRepository;

    @DisplayName("QueryDSL: 게이트웨이 ID에 해당하는 데이터 수를 조회")
    @Test
    void testCountByConditions_search_gatewayId() {
        SensorSearchRequest request = new SensorSearchRequest(
                testGatewayId,
                null,
                null,
                null
        );
    }

    /*@DisplayName("QueryDSL: 모든 검색 조건에 해당하는 데이터 하나를 특정해낸 경우")
    @Test
    void testCountByConditions_search_all() {
        SensorSearchRequest request = new SensorSearchRequest(
                testGatewayId,
                testSensorId,
                testSensorLocation,
                testSensorSpot
        );
        long searchAll = sensorRepository.countByConditions(request);
        Assertions.assertEquals(1L, searchAll);
    }*/

    @DisplayName("QueryDSL: 모든 검색 조건에 해당하는 데이터가 없는 경우")
    @Test
    void testCountByConditions_search_all_empty() {
        SensorSearchRequest request = new SensorSearchRequest(
                testGatewayId,
                testSensorId,
                testSensorLocation,
                testSensorSpot
        );
        long empty = sensorRepository.countByConditions(request);
        Assertions.assertEquals(0L, empty);
    }

    /*@DisplayName("QueryDSL: 존재하는 센서 아이디 카운트 체크")
    @Test
    void testCountBySensorId_save() {
        Sensor sensor = Sensor.ofNewSensor(
                testGatewayId, testSensorId,
                testSensorLocation, testSensorSpot
        );
        sensorRepository.save(sensor);

        Assertions.assertNotEquals(
                0,
                sensorRepository.countBySensorId(sensor.getSensorId())
        );
    }

    @DisplayName("QueryDSL: 존재하지 않는 센서 아이디 카운트 체크")
    @Test
    void testCountBySensorId_notSave() {
        Assertions.assertEquals(
                0,
                sensorRepository.countBySensorId(testSensorId)
        );
    }

    @DisplayName("QueryDSL: 존재하는 센서 아이디 중복 체크")
    @Test
    void testExistsBySensorId_save() {
        Sensor sensor = Sensor.ofNewSensor(
                testGatewayId, testSensorId,
                testSensorLocation, testSensorSpot
        );
        sensorRepository.save(sensor);

        Assertions.assertTrue(
                sensorRepository.existsBySensorId(sensor.getSensorId())
        );
    }

    @DisplayName("QueryDSL: 존재하지 않는 센서 아이디 중복 체크")
    @Test
    void testExistsBySensorId_notSave() {
        Assertions.assertFalse(
                sensorRepository.existsBySensorId(testSensorId)
        );
    }*/


    /*@DisplayName("QueryDSL: 모든 Entity 데이터들에서 센서 ID만 가져와 Set<sensorId> 처리")
    @ParameterizedTest
    @MethodSource("sensors")
    void testFindDistinctSensorIds(List<Sensor> sensors) {
        sensorRepository.saveAll(sensors);

        Set<String> findSensorIds = sensorRepository.findDistinctSensorIds();
        log.debug("sensors: {}", findSensorIds);
        Assertions.assertEquals(sensors.size(), findSensorIds.size());

        findSensorIds.forEach(sensorId -> {
            SensorSearchRequest request = new SensorSearchRequest(
                    null, sensorId, null, null
            );

            /// TODO: 테스트 코드 조정 예정...
            SensorInfoResponse actual = sensorRepository.findByConditions(request).getFirst();
            log.debug("sensor: {}", actual);

            Assertions.assertNotNull(actual);
            Assertions.assertNotEquals(
                    0,
                    sensors.stream()
                            .filter(sensor ->
                                    sensor.getSensorId().equals(actual.getSensorId())
                            )
                            .count()
            );
        });
    }*/

    private static Stream<Arguments> sensors() {
        return Stream.of(
                Arguments.of(
                        SensorTestingData.sensors()
                )
        );
    }
}
