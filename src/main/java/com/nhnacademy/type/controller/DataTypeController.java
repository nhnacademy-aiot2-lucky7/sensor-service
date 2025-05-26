package com.nhnacademy.type.controller;

import com.nhnacademy.type.domain.DataTypeInfo;
import com.nhnacademy.type.dto.DataTypeInfoResponse;
import com.nhnacademy.type.service.DataTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data-types")
public class DataTypeController {

    private final DataTypeService dataTypeService;

    public DataTypeController(DataTypeService dataTypeService) {
        this.dataTypeService = dataTypeService;
    }

    @GetMapping("/{type-en-name}")
    public ResponseEntity<DataTypeInfoResponse> getDataTypeInfoResponse(
            @PathVariable("type-en-name") String dataTypeEnName
    ) {
        return ResponseEntity
                .ok(dataTypeService.getDataTypeInfoResponse(dataTypeEnName));
    }

    @PostMapping
    public ResponseEntity<Void> registerDataType(
            @Validated @RequestBody DataTypeInfo request
    ) {
        dataTypeService.registerRequest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping
    public ResponseEntity<Void> updateDataType(
            @Validated @RequestBody DataTypeInfo request
    ) {
        dataTypeService.updateDataType(request);
        return ResponseEntity
                .noContent()
                .build();
    }

    /// TODO: 삭제의 경우는 SensorDataMapping랑 연계해서 삭제해야 합니다.
}
