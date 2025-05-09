package com.nhnacademy.type.repository;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.type.domain.DataType;
import jakarta.persistence.EntityManager;
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

    private static final String TEST_EN_NAME = "test-en-name";

    private static final String TEST_KR_NAME = "test-kr-name";

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private EntityManager em;

    private DataType test;

    @BeforeEach
    void setUp() {
        test = DataType.ofNewDataType(TEST_EN_NAME, TEST_KR_NAME);
    }

    @DisplayName("JPA: 삽입 테스트")
    @Test
    void testCreate() {
        dataTypeRepository.save(test);

        log.debug("create actual: {}", test);
        Assertions.assertTrue(dataTypeRepository.findById(test.getDataTypeEnName()).isPresent());
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
