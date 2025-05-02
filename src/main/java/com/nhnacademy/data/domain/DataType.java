package com.nhnacademy.data.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "data_types")
@Getter
@ToString
public class DataType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_no", nullable = false)
    @Comment("데이터_타입_번호")
    private Integer dataTypeNo;

    @Column(name = "type_en_name", length = 30, nullable = false)
    @Comment("데이터_타입_원문")
    private String dataTypeEnName;

    @Column(name = "type_kr_name", length = 30, nullable = false)
    @Comment("데이터_타입_한글")
    private String dataTypeKrName;

    protected DataType() {
    }

    private DataType(String dataTypeEnName, String dataTypeKrName) {
        this.dataTypeEnName = dataTypeEnName;
        this.dataTypeKrName = dataTypeKrName;
    }

    /**
     * 정적 팩토리 메서드입니다. 한글명이 "미정"으로 설정된 새로운 데이터 타입을 생성합니다.
     *
     * @param dataTypeEnName 데이터 타입의 원문 이름 (예: "temp", "humidity" 등)
     * @return 새로 생성된 {@link DataType} 인스턴스
     */
    public static DataType ofNewDataType(String dataTypeEnName) {
        return ofNewDataType(dataTypeEnName, "미정");
    }

    /**
     * 정적 팩토리 메서드입니다. 주어진 원문명과 한글명으로 새로운 데이터 타입을 생성합니다.
     *
     * @param dataTypeEnName 데이터 타입의 원문 이름
     * @param dataTypeKrName 데이터 타입의 한글 이름
     * @return 새로 생성된 {@link DataType} 인스턴스
     */
    public static DataType ofNewDataType(String dataTypeEnName, String dataTypeKrName) {
        return new DataType(dataTypeEnName, dataTypeKrName);
    }

    /**
     * 데이터 타입의 원문명을 수정합니다.
     *
     * @param dataTypeEnName 새로 설정할 데이터 타입 원문명
     */
    public void updateTypeEnName(String dataTypeEnName) {
        this.dataTypeEnName = dataTypeEnName;
    }

    /**
     * 데이터 타입의 한글명을 수정합니다.
     *
     * @param dataTypeKrName 새로 설정할 데이터 타입 한글명
     */
    public void updateTypeKrName(String dataTypeKrName) {
        this.dataTypeKrName = dataTypeKrName;
    }

    /**
     * 데이터 타입의 원문명과 한글명을 함께 수정합니다.
     *
     * @param dataTypeEnName 새로 설정할 데이터 타입 원문명
     * @param dataTypeKrName 새로 설정할 데이터 타입 한글명
     */
    public void updateTypeInfo(String dataTypeEnName, String dataTypeKrName) {
        updateTypeEnName(dataTypeEnName);
        updateTypeKrName(dataTypeKrName);
    }

    /**
     * 주어진 한글명이 현재 데이터 타입의 한글명과 같은지 비교합니다.
     *
     * @param dataTypeKrName 비교할 데이터 타입 한글명
     * @return 동일하면 {@code true}, 다르면 {@code false}
     */
    public boolean isSameTypeKrName(String dataTypeKrName) {
        return this.dataTypeKrName.equals(dataTypeKrName);
    }
}
