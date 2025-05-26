package com.nhnacademy.type.service;

import com.nhnacademy.common.exception.http.extend.DataTypeNotFoundException;
import com.nhnacademy.type.DataTypeTestingData;
import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.repository.DataTypeRepository;
import com.nhnacademy.type.service.impl.DataTypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DataTypeServiceTest {

    private final String testEnName = DataTypeTestingData.TEST_EN_NAME;

    @Mock
    private DataTypeRepository dataTypeRepository;

    @InjectMocks
    private DataTypeServiceImpl dataTypeService;

    @DisplayName("DataType 서비스: enName")
    @Test
    void testGetDataType_success() {
        /// given
        DataType mockDataType = testDataType();
        Optional<DataType> mockOptionalDataType = Optional.of(mockDataType);

        Mockito.when(dataTypeRepository.findById(mockDataType.getDataTypeEnName()))
                .thenReturn(mockOptionalDataType);

        /// when
        DataType dataType = dataTypeService.getDataType(mockDataType.getDataTypeEnName());
        log.debug("find dataType: {}", dataType);

        /// then
        Mockito.verify(
                        dataTypeRepository,
                        Mockito.times(1)
                )
                .findById(mockDataType.getDataTypeEnName());

        Assertions.assertAll(
                () -> Assertions.assertEquals(mockDataType.getDataTypeEnName(), dataType.getDataTypeEnName()),
                () -> Assertions.assertEquals(mockDataType.getDataTypeKrName(), dataType.getDataTypeKrName())
        );
    }

    @DisplayName("DataType 서비스: enName")
    @Test
    void testGetDataType_failed() {
        /// given
        Mockito.when(dataTypeRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        /// when
        Assertions.assertThrows(
                DataTypeNotFoundException.class,
                () -> dataTypeService.getDataType(testEnName)
        );

        /// then
        Mockito.verify(
                        dataTypeRepository,
                        Mockito.times(1)
                )
                .findById(testEnName);
    }

    @DisplayName("DataType 서비스: enName 존재 유무 검증")
    @ParameterizedTest
    @MethodSource("testingData")
    void testIsExistsDataType(boolean mockExists) {
        /// given
        Mockito.when(dataTypeRepository.existsById(testEnName))
                .thenReturn(mockExists);

        /// when
        boolean existsEnName = dataTypeService.isExistsDataType(testEnName);
        log.debug("enName is exists: {}", existsEnName);

        /// then
        Mockito.verify(
                        dataTypeRepository,
                        Mockito.times(1)
                )
                .existsById(testEnName);

        Assertions.assertEquals(mockExists, existsEnName);
    }

    private DataType testDataType() {
        return DataType.ofNewDataType(
                testEnName,
                DataTypeTestingData.TEST_KR_NAME
        );
    }

    private static Stream<Arguments> testingData() {
        return Stream.of(
                Arguments.of(true),
                Arguments.of(false)
        );
    }
}
