package com.nhnacademy.sensor;

import com.nhnacademy.sensor.domain.Sensor;

import java.util.ArrayList;
import java.util.List;

public final class SensorTestingData {

    public static final String TEST_GATEWAY_ID = "test-gateway-id";

    public static final String TEST_SENSOR_ID = "test-sensor-id";

    public static final String TEST_SENSOR_LOCATION = "test-sensor-location";

    public static final String TEST_SENSOR_SPOT = "test-sensor-spot";

    private SensorTestingData() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Sensor> sensors() {
        int index = 0;
        List<Sensor> sensors = new ArrayList<>();
        for (int n = 1; n < 4; n++) {
            for (int m = 0; m < 2; m++) {
                sensors.add(
                        Sensor.ofNewSensor(
                                "%s-%d".formatted(SensorTestingData.TEST_GATEWAY_ID, n),
                                "%s-%d".formatted(SensorTestingData.TEST_SENSOR_ID, ++index),
                                SensorTestingData.TEST_SENSOR_LOCATION,
                                SensorTestingData.TEST_SENSOR_SPOT
                        )
                );
            }
        }
        return sensors;
    }
}
