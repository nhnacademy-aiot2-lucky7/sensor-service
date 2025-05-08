package com.nhnacademy.type.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "type_en_name", length = 30, nullable = false)
    @Comment("데이터_타입_영문명")
    private String dataTypeEnName;

    @Column(name = "type_kr_name", length = 30, nullable = false)
    @Comment("데이터_타입_한글명")
    private String dataTypeKrName;

    /**
     * JPA 전용 기본 생성자입니다.
     */
    protected DataType() {
    }

    /**
     * 데이터 타입의 모든 주요 정보를 기반으로 새로운 데이터 타입 엔티티를 생성합니다. <br>
     * 외부에서는 직접 호출하지 않고, 정적 팩토리 메서드를 통해 인스턴스를 생성하십시오.
     *
     * @param dataTypeEnName 데이터 타입의 영문 이름
     * @param dataTypeKrName 데이터 타입의 한글 이름
     * @see #ofNewDataType(String)
     */
    private DataType(String dataTypeEnName, String dataTypeKrName) {
        this.dataTypeEnName = dataTypeEnName;
        this.dataTypeKrName = dataTypeKrName;
    }

    /**
     * <b>정적 팩토리 메서드입니다.</b> <br>
     * 주어진 원문명으로 새로운 객체를 생성합니다. <br>
     * 한글명이 "미정"으로 설정된 새로운 데이터 타입을 생성합니다.
     *
     * @param dataTypeEnName 데이터 타입의 영문 명칭
     * @return 새로 생성된 {@link DataType} 인스턴스
     */
    public static DataType ofNewDataType(String dataTypeEnName) {
        return ofNewDataType(dataTypeEnName, "미정");
    }

    /**
     * <b>정적 팩토리 메서드입니다.</b> <br>
     * 주어진 원문명과 한글명으로 새로운 객체를 생성합니다.
     *
     * @param dataTypeEnName 데이터 타입의 영문 이름
     * @param dataTypeKrName 데이터 타입의 한글 이름
     * @return 새로 생성된 {@link DataType} 인스턴스
     */
    public static DataType ofNewDataType(String dataTypeEnName, String dataTypeKrName) {
        return new DataType(dataTypeEnName, dataTypeKrName);
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
     * 주어진 영문명이 현재 데이터 타입의 영문명과 같은지 비교합니다.
     *
     * @param dataTypeEnName 비교할 데이터 타입 영문명
     * @return 동일하면 {@code true}, 다르면 {@code false}
     */
    public boolean hasEnName(String dataTypeEnName) {
        return this.dataTypeEnName.equals(dataTypeEnName);
    }

    /**
     * 주어진 한글명이 현재 데이터 타입의 한글명과 같은지 비교합니다.
     *
     * @param dataTypeKrName 비교할 데이터 타입 한글명
     * @return 동일하면 {@code true}, 다르면 {@code false}
     */
    public boolean hasKrName(String dataTypeKrName) {
        return this.dataTypeKrName.equals(dataTypeKrName);
    }
}
