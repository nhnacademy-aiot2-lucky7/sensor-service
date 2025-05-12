package com.nhnacademy.type.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.type.domain.DataType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@CustomDataJpaTest
class CustomDataTypeRepositoryTest {

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @DisplayName("QueryDSL: 모든 Entity를 Map<enName, krName> 구조로 조회")
    @ParameterizedTest
    @MethodSource("dataTypes")
    void testFindAllAsMap(List<DataType> dataTypes) {
        dataTypeRepository.saveAll(dataTypes);

        Map<String, String> findAll = dataTypeRepository.findAllAsMap();
        log.debug("dataTypes: {}", findAll);
        Assertions.assertEquals(dataTypes.size(), findAll.size());

        findAll.forEach((enName, krName) -> {
            Optional<DataType> optional = dataTypeRepository.findById(enName);
            Assertions.assertTrue(optional.isPresent());

            DataType dataType = optional.get();
            log.debug("dataType: {}", dataType);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(enName, dataType.getDataTypeEnName()),
                    () -> Assertions.assertEquals(krName, dataType.getDataTypeKrName())
            );
        });
    }

    private static Stream<Arguments> dataTypes() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                DataType.ofNewDataType("temperature", "온도"),
                                DataType.ofNewDataType("humidity", "습도"),
                                DataType.ofNewDataType("co2", "이산화탄소")
                        )
                )
        );
    }
}
