package com.nhnacademy.type.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class DataTypeInfo {

    @NotBlank(message = "데이터 타입의 영문명이 누락되었습니다.")
    @Size(min = 2, max = 30, message = "데이터 타입의 영문명은 2자 이상 30자 이하로 입력해야 합니다.")
    @JsonProperty("type_en_name")
    String dataTypeEnName;

    @Size(min = 2, max = 30, message = "데이터 타입의 한글명은 2자 이상 30자 이하로 입력해야 합니다.")
    @JsonProperty("type_kr_name")
    String dataTypeKrName;
}
