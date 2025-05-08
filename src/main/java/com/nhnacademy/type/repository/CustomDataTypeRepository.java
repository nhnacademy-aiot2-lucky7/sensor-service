package com.nhnacademy.type.repository;

import com.nhnacademy.type.domain.DataType;

import java.util.Map;

public interface CustomDataTypeRepository {

    /**
     * SELECT COUNT(type_en_name)
     * FROM data_types
     * WHERE type_kr_name = ?
     */
    long countByDataTypeKrName(String dataTypeKrName);

    /**
     * {@link CustomDataTypeRepository#countByDataTypeKrName(String dataTypeKrName)} > 0
     */
    boolean existsByDataTypeKrName(String dataTypeKrName);

    /**
     * SELECT *
     * FROM data_types
     * WHERE type_kr_name = ?
     */
    DataType findByDataTypeKrName(String dataTypeKrName);

    /**
     * SELECT *
     * FROM data_types
     * <hr>
     * {@code Key}: type_en_name / {@code Value}: type_kr_name
     */
    Map<String, String> findAllAsMap();
}
