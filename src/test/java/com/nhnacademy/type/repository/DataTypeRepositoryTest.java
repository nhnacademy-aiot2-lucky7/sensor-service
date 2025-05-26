package com.nhnacademy.type.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.type.DataTypeTestingData;
import com.nhnacademy.type.domain.DataType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
@CustomDataJpaTest
class DataTypeRepositoryTest {

    @Autowired
    private DataTypeRepository dataTypeRepository;

    private DataType test;

    @BeforeEach
    void setUp() {
        test = DataType.ofNewDataType(
                DataTypeTestingData.TEST_EN_NAME,
                DataTypeTestingData.TEST_KR_NAME
        );
    }

    @DisplayName("JPA: 삽입 테스트")
    @Test
    void testCreate() {
        DataType dataType = dataTypeRepository.save(test);
        log.debug("create actual: {}", dataType);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(dataType),
                () -> Assertions.assertTrue(dataTypeRepository.findById(dataType.getDataTypeEnName()).isPresent())
        );
    }

    @DisplayName("JPA: 조회 테스트")
    @Test
    void testRead() {
        dataTypeRepository.save(test);

        DataType actual = get(test.getDataTypeEnName());
        log.debug("read actual: {}", actual);
        equals(test, actual);
    }

    @DisplayName("JPA: 수정 테스트")
    @Test
    void testUpdate() {
        String krName = "습도";

        dataTypeRepository.save(test);

        DataType testUpdate = get(test.getDataTypeEnName());
        testUpdate.updateTypeKrName(krName);
        dataTypeRepository.flush();

        DataType actual = get(test.getDataTypeEnName());
        log.debug("update actual: {}", get(test.getDataTypeEnName()));
        equals(testUpdate, actual);
    }

    @DisplayName("JPA: 삭제 테스트")
    @Test
    void testDelete() {
        dataTypeRepository.save(test);

        DataType delete = get(test.getDataTypeEnName());
        dataTypeRepository.deleteById(delete.getDataTypeEnName());
        log.debug("delete actual: {}", delete);

        Optional<DataType> actual = dataTypeRepository.findById(test.getDataTypeEnName());
        Assertions.assertTrue(actual.isEmpty());
    }

    private DataType get(String dataTypeEnName) {
        Optional<DataType> optional = dataTypeRepository.findById(dataTypeEnName);
        Assertions.assertTrue(optional.isPresent());
        return optional.get();
    }

    private void equals(DataType expected, DataType actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getDataTypeEnName(), actual.getDataTypeEnName()),
                () -> Assertions.assertEquals(expected.getDataTypeKrName(), actual.getDataTypeKrName())
        );
    }
}
