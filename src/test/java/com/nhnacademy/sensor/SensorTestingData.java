package com.nhnacademy.sensor;

import com.nhnacademy.sensor.domain.Sensor;

import java.util.ArrayList;
import java.util.List;

public final class SensorTestingData {

    public static final long TEST_GATEWAY_ID = 1L;

    public static final String TEST_SENSOR_ID = "test-sensor-id";

    public static final String TEST_SENSOR_LOCATION = "test-sensor-location";

    public static final String TEST_SENSOR_SPOT = "test-sensor-spot";

    private SensorTestingData() {
        throw new IllegalStateException("Utility class");
    }

    public static Sensor sample() {
        return Sensor.ofNewSensor(
                TEST_GATEWAY_ID,
                TEST_SENSOR_ID,
                TEST_SENSOR_LOCATION,
                TEST_SENSOR_SPOT
        );
    }

    public static List<Sensor> samples() {
        String[][] loc = {
                {
                        "%s-A".formatted(TEST_SENSOR_LOCATION),
                        "%s-A".formatted(TEST_SENSOR_LOCATION),
                        null,
                        null
                },
                {
                        "%s-B".formatted(TEST_SENSOR_LOCATION),
                        "%s-B".formatted(TEST_SENSOR_LOCATION),
                        null,
                        null
                }
        };
        String[] spot = {
                "%s-A".formatted(TEST_SENSOR_SPOT),
                null,
                "%s-B".formatted(TEST_SENSOR_SPOT),
                null
        };

        List<Sensor> sensors = new ArrayList<>();
        for (int n = 0; n < 2; n++) {
            for (int m = 0; m < 4; m++) {
                sensors.add(
                        Sensor.ofNewSensor(
                                TEST_GATEWAY_ID + n,
                                "%s-%d".formatted(TEST_SENSOR_ID, m + 1),
                                loc[n][m],
                                spot[m]
                        )
                );
            }
        }
        return sensors;
    }
}
