package com.nhnacademy.data.domain;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
class DataTypeTest {

    private static final String TEST_EN_NAME = "test-en-name";

    private static final String TEST_KR_NAME = "test-kr-name";

    @Autowired
    private EntityManager em;

    @DisplayName("생성자 테스트: 기본 파라미터 테스트")
    @Test
    void testStaticConstructor1() {
        DataType actual = DataType.ofNewDataType(TEST_EN_NAME);
        Assertions.assertEquals(TEST_EN_NAME, actual.getDataTypeEnName());
        Assertions.assertTrue(actual.isSameTypeKrName("미정"));
    }

    @DisplayName("생성자 테스트: 모든 파라미터 주입 테스트")
    @Test
    void testStaticConstructor2() {
        DataType actual = DataType.ofNewDataType(TEST_EN_NAME, TEST_KR_NAME);
        Assertions.assertEquals(TEST_EN_NAME, actual.getDataTypeEnName());
        Assertions.assertTrue(actual.isSameTypeKrName(TEST_KR_NAME));
    }

    @DisplayName("Entity: 삽입 테스트")
    @Test
    void testCreate() {
        DataType testSave = DataType.ofNewDataType(TEST_EN_NAME);
        em.persist(testSave);

        log.debug("{}", testSave);
        Assertions.assertNotNull(testSave.getDataTypeNo());
    }

    @DisplayName("Entity: 조회 테스트")
    @Test
    void testRead() {
        DataType testRead = DataType.ofNewDataType(TEST_EN_NAME);
        em.persist(testRead);

        DataType actual = em.find(DataType.class, testRead.getDataTypeNo());
        log.debug("find to read entity: {}", actual);

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(testRead.getDataTypeEnName(), actual.getDataTypeEnName()),
                () -> Assertions.assertTrue(testRead.isSameTypeKrName(actual.getDataTypeKrName()))
        );
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String enName = "temperature";
        String krName = "온도";

        DataType testUpdate = DataType.ofNewDataType(TEST_EN_NAME);
        em.persist(testUpdate);

        testUpdate.updateTypeInfo(enName, krName);
        em.flush();
        em.clear();

        DataType actual = em.find(DataType.class, testUpdate.getDataTypeNo());
        log.debug("find to update entity: {}", actual);

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(testUpdate.getDataTypeEnName(), actual.getDataTypeEnName()),
                () -> Assertions.assertTrue(testUpdate.isSameTypeKrName(actual.getDataTypeKrName()))
        );
    }

    @DisplayName("Entity: 삭제 테스트")
    @Test
    void testDelete() {
        DataType testDelete = DataType.ofNewDataType(TEST_EN_NAME);
        em.persist(testDelete);

        Assertions.assertNotNull(em.find(DataType.class, testDelete.getDataTypeNo()));
        em.remove(testDelete);
        em.flush();
        em.clear();

        DataType actual = em.find(DataType.class, testDelete.getDataTypeNo());
        Assertions.assertNull(actual);
    }
}
