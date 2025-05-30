package com.nhnacademy.sensor.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorIndexResponse;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@CustomDataJpaTest
class CustomSensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    /// TODO: 추후에 다양한 테스트 구조를 추가할 예정...

    @DisplayName("Sensor QueryDSL: (gatewayId) 조건으로 countByConditions 결과 검증")
    @ParameterizedTest
    @MethodSource("testSensorSearchRequestByGatewayId")
    void testCountByConditions_search_gatewayId(SensorSearchRequest request) {
        List<Sensor> samples = SensorTestingData.samples();
        sensorRepository.saveAll(samples);

        long expected = getExpectedCount(samples, request);
        log.debug("count expected: {}", expected);

        long actual = sensorRepository.countByConditions(request);
        log.debug("count actual: {}", actual);

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("Sensor QueryDSL: (gatewayId) 조건으로 existsByConditions 결과 검증")
    @ParameterizedTest
    @MethodSource("testSensorSearchRequestByGatewayId")
    void testExistsByConditions_search_gatewayId(SensorSearchRequest request) {
        List<Sensor> samples = SensorTestingData.samples();
        sensorRepository.saveAll(samples);

        boolean actual = sensorRepository.existsByConditions(request);
        if (getExpectedCount(samples, request) > 0) {
            Assertions.assertTrue(actual);
        } else {
            Assertions.assertFalse(actual);
        }
    }

    @DisplayName("Sensor QueryDSL: (gatewayId) 조건으로 findByConditions 결과 검증")
    @ParameterizedTest
    @MethodSource("testSensorSearchRequestByGatewayId")
    void testFindByConditions_search_gatewayId(SensorSearchRequest request) {
        List<Sensor> samples = SensorTestingData.samples();
        sensorRepository.saveAll(samples);

        long expected = getExpectedCount(samples, request);

        List<SensorInfoResponse> results = sensorRepository.findByConditions(request);
        Assertions.assertEquals(expected, results.size());

        long actual = 0;
        for (SensorInfoResponse response : results) {
            for (Sensor sample : samples) {
                if (isNotEquals(sample, response)) continue;
                actual++;
            }
        }
        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> testSensorSearchRequestByGatewayId() {
        int index = 0;
        return Stream.of(
                Arguments.of(new SensorSearchRequest(
                        SensorTestingData.TEST_GATEWAY_ID + (++index),
                        null, null, null
                )),
                Arguments.of(new SensorSearchRequest(
                        SensorTestingData.TEST_GATEWAY_ID + (++index),
                        null, null, null
                )),
                Arguments.of(new SensorSearchRequest(
                        SensorTestingData.TEST_GATEWAY_ID + (++index),
                        null, null, null
                ))
        );
    }

    @DisplayName("Sensor QueryDSL: 저장된 모든 센서의 Set(gatewayId, sensorId) 조회")
    @Test
    void testFindAllSensorUniqueKeys_success() {
        List<Sensor> samples = SensorTestingData.samples();
        sensorRepository.saveAll(samples);

        Set<SensorIndexResponse> results =
                sensorRepository.findAllSensorUniqueKeys();
        Assertions.assertEquals(samples.size(), results.size());

        results.forEach(response ->
                Assertions.assertTrue(
                        sensorRepository.existsByGatewayIdAndSensorId(
                                response.getGatewayId(),
                                response.getSensorId()
                        )
                )
        );
    }

    @DisplayName("Sensor QueryDSL: 저장된 센서 데이터가 없을 시, 빈 Set 반환")
    @Test
    void testFindAllSensorUniqueKeys_empty() {
        Assertions.assertTrue(
                sensorRepository.findAllSensorUniqueKeys().isEmpty()
        );
    }

    private long getExpectedCount(List<Sensor> samples, SensorSearchRequest request) {
        long expected = 0;
        for (Sensor sample : samples) {
            if (request.isNotNullGatewayId() && !Objects.equals(sample.getGatewayId(), request.getGatewayId())) {
                continue;
            }
            if (request.isNotNullSensorId() && !Objects.equals(sample.getSensorId(), request.getSensorId())) {
                continue;
            }
            if (request.isNotNullSensorLocation() && !Objects.equals(sample.getSensorLocation(), request.getSensorLocation())) {
                continue;
            }
            if (request.isNotNullSensorSpot() && !Objects.equals(sample.getSensorSpot(), request.getSensorSpot())) {
                continue;
            }
            expected++;
        }
        return expected;
    }

    private boolean isNotEquals(Sensor sample, SensorInfoResponse response) {
        return !Objects.equals(sample.getGatewayId(), response.getGatewayId())
                || !Objects.equals(sample.getSensorId(), response.getSensorId())
                || !Objects.equals(sample.getSensorLocation(), response.getSensorLocation())
                || !Objects.equals(sample.getSensorSpot(), response.getSensorSpot());
    }
}
