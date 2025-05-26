package com.nhnacademy.type;

import com.nhnacademy.type.domain.DataType;

import java.util.List;

public final class DataTypeTestingData {

    public static final String TEST_EN_NAME = "test-en-name";

    public static final String TEST_KR_NAME = "test-kr-name";

    private DataTypeTestingData() {
        throw new IllegalStateException("Utility class");
    }

    public static List<DataType> dataTypes() {
        return List.of(
                DataType.ofNewDataType("temperature", "온도"),
                DataType.ofNewDataType("humidity", "습도"),
                DataType.ofNewDataType("co2", "이산화탄소")
        );
    }
}
