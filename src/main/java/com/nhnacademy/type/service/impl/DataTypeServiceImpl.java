package com.nhnacademy.type.service.impl;

import com.nhnacademy.common.exception.http.extend.DataTypeAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.DataTypeNotFoundException;
import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.dto.DataTypeInfo;
import com.nhnacademy.type.dto.DataTypeInfoResponse;
import com.nhnacademy.type.repository.DataTypeRepository;
import com.nhnacademy.type.service.DataTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataTypeServiceImpl implements DataTypeService {

    private final DataTypeRepository dataTypeRepository;

    public DataTypeServiceImpl(DataTypeRepository dataTypeRepository) {
        this.dataTypeRepository = dataTypeRepository;
    }

    @Override
    public void registerRequest(DataTypeInfo request) {
        if (isExistsDataType(request.getDataTypeEnName())) {
            throw new DataTypeAlreadyExistsException(
                    request.getDataTypeEnName()
            );
        }
        registerDataType(
                request.getDataTypeEnName(),
                request.getDataTypeKrName()
        );
    }

    @Override
    public DataType registerDataType(String dataTypeEnName, String dataTypeKrName) {
        return dataTypeRepository.save(
                DataType.ofNewDataType(
                        dataTypeEnName,
                        dataTypeKrName
                )
        );
    }

    @Override
    public DataType getDataType(String dataTypeEnName) {
        return dataTypeRepository.findById(dataTypeEnName)
                .orElseThrow(() -> new DataTypeNotFoundException(dataTypeEnName));
    }

    @Override
    public DataType getReferenceDataType(String dataTypeEnName) {
        return dataTypeRepository.getReferenceById(dataTypeEnName);
    }

    @Override
    public void updateDataType(DataTypeInfo request) {
        DataType dataType = getDataType(request.getDataTypeEnName());
        dataType.updateTypeKrName(request.getDataTypeKrName());
        dataTypeRepository.flush();
    }

    @Override
    public void removeDataType(String dataTypeEnName) {
        if (!isExistsDataType(dataTypeEnName)) {
            throw new DataTypeNotFoundException(dataTypeEnName);
        }
        dataTypeRepository.deleteById(dataTypeEnName);
    }

    @Override
    public boolean isExistsDataType(String dataTypeEnName) {
        return dataTypeRepository.existsById(dataTypeEnName);
    }

    /// 상세 정보 데이터
    @Transactional(readOnly = true)
    @Override
    public DataTypeInfoResponse getDataTypeInfoResponse(String dataTypeEnName) {
        DataType dataType = getDataType(dataTypeEnName);
        return DataTypeInfoResponse.from(dataType);
    }
}
