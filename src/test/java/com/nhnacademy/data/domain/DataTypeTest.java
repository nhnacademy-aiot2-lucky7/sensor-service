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
        DataType dataType = DataType.ofNewDataType(TEST_EN_NAME);
        Assertions.assertEquals(TEST_EN_NAME, dataType.getDataTypeEnName());
        Assertions.assertTrue(dataType.isSameTypeKrName("미정"));
    }

    @DisplayName("생성자 테스트: 모든 파라미터 주입 테스트")
    @Test
    void testStaticConstructor2() {
        DataType dataType = DataType.ofNewDataType(TEST_EN_NAME, TEST_KR_NAME);
        Assertions.assertEquals(TEST_EN_NAME, dataType.getDataTypeEnName());
        Assertions.assertTrue(dataType.isSameTypeKrName(TEST_KR_NAME));
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
        DataType testRead = DataType.ofNewDataType(TEST_EN_NAME, TEST_KR_NAME);
        em.persist(testRead);

        DataType findRead = em.find(DataType.class, testRead.getDataTypeNo());
        log.debug("find to read entity: {}", findRead);

        Assertions.assertNotNull(findRead);
        Assertions.assertAll(
                () -> Assertions.assertEquals(testRead.getDataTypeEnName(), findRead.getDataTypeEnName()),
                () -> Assertions.assertTrue(testRead.isSameTypeKrName(findRead.getDataTypeKrName()))
        );
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String key = "키는 키요";
        String value = "벨류는 벨류로다.";

        DataType testUpdate = DataType.ofNewDataType(TEST_EN_NAME);
        em.persist(testUpdate);

        testUpdate.updateTypeInfo(key, value);
        em.flush();
        em.clear();

        DataType findUpdate = em.find(DataType.class, testUpdate.getDataTypeNo());
        log.debug("find to update entity: {}", findUpdate);

        Assertions.assertNotNull(findUpdate);
        Assertions.assertAll(
                () -> Assertions.assertEquals(testUpdate.getDataTypeEnName(), findUpdate.getDataTypeEnName()),
                () -> Assertions.assertTrue(testUpdate.isSameTypeKrName(findUpdate.getDataTypeKrName()))
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

        DataType findDelete = em.find(DataType.class, testDelete.getDataTypeNo());
        Assertions.assertNull(findDelete);
    }
}
