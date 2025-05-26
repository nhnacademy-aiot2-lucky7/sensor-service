package com.nhnacademy.type.domain;

import com.nhnacademy.CustomDataJpaTest;
import com.nhnacademy.type.DataTypeTestingData;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
@CustomDataJpaTest
class DataTypeTest {

    private final String testEnName = DataTypeTestingData.TEST_EN_NAME;

    private final String testKrName = DataTypeTestingData.TEST_KR_NAME;

    @Autowired
    private EntityManager em;

    @DisplayName("생성자 테스트: 파라미터 주입 테스트")
    @Test
    void testStaticConstructor() {
        DataType dataType = DataType.ofNewDataType(testEnName, testKrName);
        Assertions.assertAll(
                () -> Assertions.assertEquals(testEnName, dataType.getDataTypeEnName()),
                () -> Assertions.assertEquals(testKrName, dataType.getDataTypeKrName())
        );
    }

    @DisplayName("Entity: 삽입 테스트")
    @Test
    void testCreate() {
        DataType testSave = DataType.ofNewDataType(testEnName, testKrName);
        em.persist(testSave);

        log.debug("create entity: {}", testSave);
        Assertions.assertNotNull(em.find(DataType.class, testSave.getDataTypeEnName()));
    }

    @DisplayName("Entity: 조회 테스트")
    @Test
    void testRead() {
        DataType testRead = DataType.ofNewDataType(testEnName, testKrName);
        em.persist(testRead);

        DataType actual = em.find(DataType.class, testRead.getDataTypeEnName());
        log.debug("find read entity: {}", actual);
        equals(testRead, actual);
    }

    @DisplayName("Entity: 수정 테스트")
    @Test
    void testUpdate() {
        String enName = "temperature";
        String krName = "온도";

        DataType testUpdate = DataType.ofNewDataType(enName, enName);
        em.persist(testUpdate);

        testUpdate.updateTypeKrName(krName);
        em.flush();
        em.clear();

        DataType actual = em.find(DataType.class, testUpdate.getDataTypeEnName());
        log.debug("find update entity: {}", actual);
        equals(testUpdate, actual);
    }

    @DisplayName("Entity: 삭제 테스트")
    @Test
    void testDelete() {
        DataType testDelete = DataType.ofNewDataType(testEnName, testKrName);
        em.persist(testDelete);
        log.debug("delete entity: {}", testDelete);

        Assertions.assertNotNull(em.find(DataType.class, testDelete.getDataTypeEnName()));
        em.remove(testDelete);
        em.flush();
        em.clear();

        DataType actual = em.find(DataType.class, testDelete.getDataTypeEnName());
        Assertions.assertNull(actual);
    }

    private void equals(DataType expected, DataType actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getDataTypeEnName(), actual.getDataTypeEnName()),
                () -> Assertions.assertEquals(expected.getDataTypeKrName(), actual.getDataTypeKrName())
        );
    }
}
