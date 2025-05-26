package com.nhnacademy.threshold.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ThresholdHistoryRegisterRequest {

    @NotBlank(message = "게이트웨이 ID가 누락되었습니다.")
    @Size(min = 2, max = 50, message = "게이트웨이 ID는 2자 이상 50자 이하로 입력해야 합니다.")
    String gatewayId;

    @NotBlank(message = "센서 ID가 누락되었습니다.")
    @Size(min = 2, max = 50, message = "센서 ID는 2자 이상 50자 이하로 입력해야 합니다.")
    String sensorId;

    @Size(max = 30, message = "센서 설치 위치는 30자 이하여야 합니다.")
    String sensorLocation;

    @Size(max = 30, message = "센서 설치 지점은 30자 이하여야 합니다.")
    String sensorSpot;

    @NotBlank(message = "센서 측정 데이터의 영문 타입명이 누락되었습니다.")
    @Size(min = 2, max = 30, message = "영문 타입명은 2자 이상 30자 이하로 입력해야 합니다.")
    String dataTypeEnName;


    @NotNull(message = "최저 임계치 값을 전달받지 못했습니다.")
    Double thresholdMin;

    @NotNull(message = "최고 임계치 값을 전달받지 못했습니다.")
    Double thresholdMax;

    @NotNull(message = "평균 임계치 값을 전달받지 못했습니다.")
    Double thresholdAvg;


    @NotNull(message = "최저 임계치의 최소값을 전달받지 못했습니다.")
    Double minRangeMin;

    @NotNull(message = "최저 임계치의 최대값을 전달받지 못했습니다.")
    Double minRangeMax;


    @NotNull(message = "최고 임계치의 최소값을 전달받지 못했습니다.")
    Double maxRangeMin;

    @NotNull(message = "최고 임계치의 최대값을 전달받지 못했습니다.")
    Double maxRangeMax;


    @NotNull(message = "평균 임계치의 최소값을 전달받지 못했습니다.")
    Double avgRangeMin;

    @NotNull(message = "평균 임계치의 최대값을 전달받지 못했습니다.")
    Double avgRangeMax;


    Double deltaMin;

    Double deltaMax;

    Double deltaAvg;


    @NotNull(message = "계산된 데이터 개수를 전달받지 못했습니다.")
    Integer dataCount;

    @NotNull(message = "계산된 시간을 전달받지 못했습니다.")
    LocalDateTime calculatedAt;
}
