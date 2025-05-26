package com.nhnacademy.type.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

/**
 * 센서 데이터의 타입(예: 온도, 습도 등)을 정의하는 엔티티입니다. <br>
 *
 * <p>각 데이터 타입은 영문명과 한글명을 가지며, 센서로부터 수집되는 데이터의 종류를 구분하는 데 사용됩니다.</p>
 *
 * <p><b>테이블명:</b> {@code data_types}</p>
 */
@Entity
@Table(name = "data_types")
@Getter
@ToString
public class DataType {

    @Id
    @Column(name = "type_en_name", length = 50, nullable = false)
    @Comment("데이터_타입_영문명")
    private String dataTypeEnName;

    @Column(name = "type_kr_name", length = 50, nullable = false)
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
     * @see DataType#ofNewDataType(String, String)
     */
    private DataType(String dataTypeEnName, String dataTypeKrName) {
        this.dataTypeEnName = dataTypeEnName;
        this.dataTypeKrName = dataTypeKrName;
    }

    /// TODO: 한글명은 구글 번역기 라이브러리를 활용해 즉시, 반영한다.
    /**
     * <b>정적 팩토리 메서드입니다.</b>
     * <hr>
     * 주어진 원문명과 한글명으로 새로운 객체를 생성합니다.
     *
     * @param dataTypeEnName 데이터 타입의 영문 이름
     * @param dataTypeKrName 데이터 타입의 한글 이름
     * @return 새로 생성된 {@link DataType} 인스턴스
     */
    public static DataType ofNewDataType(String dataTypeEnName, String dataTypeKrName) {
        return new DataType(
                dataTypeEnName,
                dataTypeKrName
        );
    }

    /**
     * 데이터 타입의 한글명을 변경합니다.
     *
     * @param dataTypeKrName 새로 설정할 데이터 타입 한글명
     */
    public void updateTypeKrName(String dataTypeKrName) {
        this.dataTypeKrName = dataTypeKrName;
    }
}
