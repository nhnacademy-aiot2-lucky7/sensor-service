package com.nhnacademy.threshold.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.sensor.SensorTestingData;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.repository.SensorRepository;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.sensor_type_mapping.repository.SensorDataMappingRepository;
import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.type.DataTypeTestingData;
import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.repository.DataTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CustomDataJpaTest
class CustomThresholdHistoryRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private SensorDataMappingRepository sensorDataMappingRepository;

    @Autowired
    private ThresholdHistoryRepository thresholdHistoryRepository;

    private List<Sensor> sensors;

    private List<DataType> dataTypes;

    private List<SensorDataMapping> sensorDataMappings;

    private List<ThresholdHistory> thresholdHistories;

    @BeforeEach
    void setUp() {
        sensors = SensorTestingData.samples();
        sensorRepository.saveAll(sensors);

        dataTypes = DataTypeTestingData.dataTypes();
        dataTypeRepository.saveAll(dataTypes);

        sensorDataMappings = sensorMappings(sensors, dataTypes);
        sensorDataMappingRepository.saveAll(sensorDataMappings);

        thresholdHistories = thresholdHistories(sensorDataMappings);
        thresholdHistoryRepository.saveAll(thresholdHistories);
    }

    /*@Test
    void test() {
        thresholdHistories.forEach(thresholdHistory -> log.debug("thresholdHistory: {}", thresholdHistory));

        log.debug("======================================================================================");
        sensors.forEach(sensor -> {
            log.debug("sensorId: {}", sensor.getSensorId());
            thresholdHistoryRepository.findLatestThresholdSummariesByGatewayId(sensor.getGatewayId());
            log.debug("======================================================================================");
        });
    }*/

    private List<SensorDataMapping> sensorMappings(List<Sensor> sensors, List<DataType> dataTypes) {
        List<SensorDataMapping> result = new ArrayList<>();
        for (int n = 0; n < 6; n++) {
            for (int m = n % 2; m < n % 2 + 2; m++) {
                result.add(
                        SensorDataMapping.ofNewSensorDataType(
                                sensors.get(n),
                                dataTypes.get(m),
                                SensorStatus.PENDING
                        )
                );
            }
        }
        return result;
    }

    private List<ThresholdHistory> thresholdHistories(List<SensorDataMapping> sensorDataMappings) {
        LocalDateTime after = LocalDateTime.now();
        LocalDateTime before = after.minusDays(1);

        List<ThresholdHistory> result = new ArrayList<>();
        sensorDataMappings.forEach(sensorMapping -> {
            for (int n = 0; n < 2; n++) {
                result.add(
                        new ThresholdHistory(
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                0,
                                ((n & 1) == 0) ? before : after,
                                sensorMapping
                        )
                );
            }
        });
        return result;
    }
}
