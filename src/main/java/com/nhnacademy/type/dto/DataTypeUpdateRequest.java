package com.nhnacademy.type.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class DataTypeUpdateRequest {

    @NotBlank(message = "데이터 타입의 영문명이 누락되었습니다.")
    @Size(min = 2, max = 30, message = "데이터 타입의 영문명은 2자 이상 30자 이하로 입력해야 합니다.")
    String dataTypeEnName;

    @NotBlank(message = "데이터 타입의 한글명이 누락되었습니다.")
    @Size(min = 2, max = 30, message = "데이터 타입의 한글명은 2자 이상 30자 이하로 입력해야 합니다.")
    String dataTypeKrName;
}
