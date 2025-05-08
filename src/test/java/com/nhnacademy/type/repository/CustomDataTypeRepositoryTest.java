package com.nhnacademy.type.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.type.domain.DataType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("QueryDSL: 존재하는 데이터 타입 한글명 카운트 체크")
    @Test
    void testCountByDataTypeKrName_save() {
        String enName = "co2";
        String krName = "이산화탄소";
        DataType dataType = DataType.ofNewDataType(enName, krName);
        dataTypeRepository.save(dataType);

        Assertions.assertNotEquals(
                0,
                dataTypeRepository.countByDataTypeKrName(dataType.getDataTypeKrName())
        );
    }

    @DisplayName("QueryDSL: 존재하지 않는 데이터 타입 한글명 카운트 체크")
    @Test
    void testCountByDataTypeKrName_notSave() {
        Assertions.assertEquals(
                0,
                dataTypeRepository.countByDataTypeKrName("이산화탄소")
        );
    }

    @DisplayName("QueryDSL: 존재하는 데이터 타입 한글명 중복 체크")
    @Test
    void testExistsByDataTypeKrName_save() {
        String enName = "temperature";
        String krName = "온도";
        DataType dataType = DataType.ofNewDataType(enName, krName);
        dataTypeRepository.save(dataType);

        Assertions.assertTrue(
                dataTypeRepository.existsByDataTypeKrName(dataType.getDataTypeKrName())
        );
    }

    @DisplayName("QueryDSL: 존재하지 않는 데이터 타입 한글명 중복 체크")
    @Test
    void testExistsByDataTypeKrName_notSave() {
        Assertions.assertFalse(
                dataTypeRepository.existsByDataTypeKrName("온도")
        );
    }

    @DisplayName("QueryDSL: 존재하는 데이터 타입 한글명으로 Entity 조회")
    @Test
    void testFindByDataTypeKrName_save() {
        String enName = "humidity";
        String krName = "습도";
        DataType expected = DataType.ofNewDataType(enName, krName);

        dataTypeRepository.save(expected);
        log.debug("expected: {}", expected);

        DataType actual = dataTypeRepository.findByDataTypeKrName(krName);
        log.debug("actual: {}", actual);

        Assertions.assertAll(
                () -> Assertions.assertTrue(actual.hasEnName(expected.getDataTypeEnName())),
                () -> Assertions.assertTrue(actual.hasKrName(expected.getDataTypeKrName()))
        );
    }

    @DisplayName("QueryDSL: 존재하지 않는 데이터 타입 한글명으로 Entity 조회")
    @Test
    void testFindByDataTypeKrName_notSave() {
        DataType notFind = dataTypeRepository.findByDataTypeKrName("습도");
        log.debug("notFind: {}", notFind);
        Assertions.assertNull(notFind);
    }

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
                    () -> Assertions.assertTrue(dataType.hasEnName(enName)),
                    () -> Assertions.assertTrue(dataType.hasKrName(krName))
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
