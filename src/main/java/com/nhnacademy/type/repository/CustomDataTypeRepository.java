package com.nhnacademy.type.repository;

import java.util.Map;

public interface CustomDataTypeRepository {

    /**
     * SELECT *
     * FROM data_types
     * <hr>
     * {@code Key}: type_en_name / {@code Value}: type_kr_name
     */
    Map<String, String> findAllAsMap();
}
