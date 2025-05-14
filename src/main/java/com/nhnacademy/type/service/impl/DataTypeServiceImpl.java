package com.nhnacademy.type.service.impl;

import com.nhnacademy.common.exception.http.extend.DataTypeAlreadyExistsException;
import com.nhnacademy.common.exception.http.extend.DataTypeNotFoundException;
import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.dto.DataTypeInfoResponse;
import com.nhnacademy.type.dto.DataTypeRegisterRequest;
import com.nhnacademy.type.dto.DataTypeUpdateRequest;
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
    public void registerRequest(DataTypeRegisterRequest request) {
        if (isExistsDataType(request.getDataTypeEnName())) {
            throw new DataTypeAlreadyExistsException(request);
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
                        dataTypeEnName, dataTypeKrName
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
    public void updateDataType(DataTypeUpdateRequest request) {
        DataType dataType = getDataType(request.getDataTypeEnName());
        dataType.updateTypeKrName(request.getDataTypeKrName());
        dataTypeRepository.flush();
    }

    @Override
    public void removeDataType(String dataTypeEnName) {
        dataTypeRepository.delete(getDataType(dataTypeEnName));
    }

    @Override
    public boolean isExistsDataType(String dataTypeEnName) {
        return dataTypeRepository.existsById(dataTypeEnName);
    }

    @Transactional(readOnly = true)
    @Override
    public DataTypeInfoResponse getDataTypeInfoResponse(String dataTypeEnName) {
        return DataTypeInfoResponse.from(getDataType(dataTypeEnName));
    }
}
