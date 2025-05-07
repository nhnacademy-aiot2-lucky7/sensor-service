package com.nhnacademy.data.repository;

import com.nhnacademy.data.domain.DataType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@Slf4j
@DataJpaTest
class DataTypeRepositoryTest {

    private static final String TEST_EN_NAME = "test-en-name";

    private static final String TEST_KR_NAME = "test-kr-name";

    @Autowired
    private DataTypeRepository dataTypeRepository;

    private DataType test;

    @BeforeEach
    void setUp() {
        test = DataType.ofNewDataType(TEST_EN_NAME, TEST_KR_NAME);
    }

    @DisplayName("Entity: 삽입 테스트")
    @Test
    void testCreate() {
        Assertions.assertNull(test.getDataTypeNo());
        dataTypeRepository.save(test);

        log.debug("{}", test);
        Assertions.assertNotNull(test);
    }

    @DisplayName("Entity: 조회 테스트")
    @Test
    void testRead() {
        dataTypeRepository.save(test);

        DataType actual = get(test.getDataTypeNo());
        log.debug("read actual: {}", actual);

        Assertions.assertAll(
                () -> Assertions.assertEquals(test.getDataTypeEnName(), actual.getDataTypeEnName()),
                () -> Assertions.assertTrue(test.isSameTypeKrName(actual.getDataTypeKrName()))
        );
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String enName = "humidity";
        String krName = "습도";

        dataTypeRepository.save(test);
        test.updateTypeInfo(enName, krName);
        dataTypeRepository.flush();

        DataType actual = get(test.getDataTypeNo());
        log.debug("update actual: {}", actual);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(TEST_EN_NAME, actual.getDataTypeEnName()),
                () -> Assertions.assertFalse(actual.isSameTypeKrName(TEST_KR_NAME))
        );
    }

    @DisplayName("Entity: 삭제 테스트")
    @Test
    void testDelete() {
        dataTypeRepository.save(test);
        Assertions.assertNotNull(test.getDataTypeNo());

        DataType delete = get(test.getDataTypeNo());
        dataTypeRepository.deleteById(delete.getDataTypeNo());

        Optional<DataType> actual = dataTypeRepository.findById(test.getDataTypeNo());
        Assertions.assertTrue(actual.isEmpty());
    }

    private DataType get(Integer dataTypeNo) {
        Optional<DataType> optional = dataTypeRepository.findById(dataTypeNo);
        Assertions.assertTrue(optional.isPresent());
        return optional.get();
    }
}
