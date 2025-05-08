package com.nhnacademy.type.domain;

import com.nhnacademy.CustomDataJpaTest;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@CustomDataJpaTest
class DataTypeTest {

    private static final String TEST_EN_NAME = "test-en-name";

    private static final String TEST_KR_NAME = "test-kr-name";

    @Autowired
    private EntityManager em;

    @DisplayName("생성자 테스트: 파라미터 주입 테스트 1")
    @Test
    void testStaticConstructor1() {
        DataType dataType = DataType.ofNewDataType(TEST_EN_NAME);
        Assertions.assertAll(
                () -> Assertions.assertTrue(dataType.hasEnName(TEST_EN_NAME)),
                () -> Assertions.assertTrue(dataType.hasKrName("미정"))
        );
    }

    @DisplayName("생성자 테스트: 파라미터 주입 테스트 2")
    @Test
    void testStaticConstructor2() {
        DataType dataType = DataType.ofNewDataType(TEST_EN_NAME, TEST_KR_NAME);
        Assertions.assertAll(
                () -> Assertions.assertTrue(dataType.hasEnName(TEST_EN_NAME)),
                () -> Assertions.assertTrue(dataType.hasKrName(TEST_KR_NAME))
        );
    }

    @DisplayName("Entity: 삽입 테스트")
    @Test
    void testCreate() {
        DataType testSave = DataType.ofNewDataType(TEST_EN_NAME);
        em.persist(testSave);

        log.debug("create entity: {}", testSave);
        Assertions.assertNotNull(em.find(DataType.class, testSave.getDataTypeEnName()));
    }

    @DisplayName("Entity: 조회 테스트")
    @Test
    void testRead() {
        DataType testRead = DataType.ofNewDataType(TEST_EN_NAME);
        em.persist(testRead);

        DataType actual = em.find(DataType.class, testRead.getDataTypeEnName());
        log.debug("find read entity: {}", actual);

        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertTrue(actual.hasEnName(testRead.getDataTypeEnName())),
                () -> Assertions.assertTrue(actual.hasKrName(testRead.getDataTypeKrName()))
        );
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String enName = "temperature";
        String krName = "온도";

        DataType testUpdate = DataType.ofNewDataType(enName);
        em.persist(testUpdate);

        testUpdate.updateTypeKrName(krName);
        em.flush();
        em.clear();

        DataType actual = em.find(DataType.class, testUpdate.getDataTypeEnName());
        log.debug("find update entity: {}", actual);

        Assertions.assertAll(
                () -> Assertions.assertTrue(actual.hasEnName(testUpdate.getDataTypeEnName())),
                () -> Assertions.assertTrue(actual.hasKrName(testUpdate.getDataTypeKrName()))
        );
    }

    @DisplayName("Entity: 삭제 테스트")
    @Test
    void testDelete() {
        DataType testDelete = DataType.ofNewDataType(TEST_EN_NAME);
        em.persist(testDelete);
        log.debug("delete entity: {}", testDelete);

        Assertions.assertNotNull(em.find(DataType.class, testDelete.getDataTypeEnName()));
        em.remove(testDelete);
        em.flush();
        em.clear();

        DataType actual = em.find(DataType.class, testDelete.getDataTypeEnName());
        Assertions.assertNull(actual);
    }
}
