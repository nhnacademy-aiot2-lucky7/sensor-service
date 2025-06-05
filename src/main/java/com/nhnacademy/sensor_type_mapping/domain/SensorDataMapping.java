package com.nhnacademy.sensor_type_mapping.domain;

import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.type.domain.DataType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

/**
 * 센서와 데이터 타입 간의 매핑 정보를 나타내는 엔티티입니다.
 *
 * <p>각 매핑은 특정 센서가 어떤 데이터 타입을 측정하는지를 나타내며,
 * 센서의 상태는 {@link SensorStatus} 열거형을 통해 관리됩니다.</p>
 *
 * <p>본 엔티티는 센서와 데이터 타입 간의 N:M 관계를 명시적 매핑 테이블로 표현하며, <br>
 * 매핑 상태 변경 등 추가 속성을 가질 수 있습니다.</p>
 *
 * <p><b>테이블명:</b> {@code sensor_data_mappings}</p>
 *
 * @see Sensor
 * @see DataType
 * @see SensorStatus
 */
@Entity
@Table(
        name = "sensor_data_mappings",
        indexes = @Index(
                name = "uk_sensor_no_and_type_en_name",
                columnList = "sensor_no, type_en_name",
                unique = true
        )
)
@Getter
public class SensorDataMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_data_no", nullable = false)
    @Comment("센서_데이터_번호")
    private Long sensorDataNo;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "sensor_status", nullable = false)
    @Comment("센서_상태")
    private SensorStatus sensorStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sensor_no", referencedColumnName = "sensor_no",
            nullable = false, updatable = false
    )
    @Comment("센서_번호")
    @ToString.Exclude
    private Sensor sensor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "type_en_name", referencedColumnName = "type_en_name",
            nullable = false, updatable = false
    )
    @Comment("데이터_타입_영문명")
    @ToString.Exclude
    private DataType dataType;

    /**
     * JPA 전용 기본 생성자입니다.
     */
    protected SensorDataMapping() {
    }

    /**
     * 지정된 센서, 데이터 타입, 센서 상태를 기반으로 새로운 매핑 엔티티를 생성합니다. <br>
     * 외부에서는 직접 호출하지 않고, 정적 팩토리 메서드를 통해 인스턴스를 생성하십시오.
     *
     * @param sensor       센서 엔티티
     * @param dataType     데이터 타입 엔티티
     * @param sensorStatus 센서 상태
     * @see SensorDataMapping#ofNewSensorDataType(Sensor, DataType, SensorStatus)
     */
    private SensorDataMapping(Sensor sensor, DataType dataType, SensorStatus sensorStatus) {
        this.sensor = sensor;
        this.dataType = dataType;
        this.sensorStatus = sensorStatus;
    }

    /**
     * <b>정적 팩토리 메서드입니다.</b>
     * <hr>
     * 주어진 센서와 데이터 타입, 센서 상태를 명시하여 새로운 매핑 인스턴스를 생성합니다.
     *
     * @param sensor       센서 엔티티
     * @param dataType     데이터 타입 엔티티
     * @param sensorStatus 센서 상태
     * @return 새로 생성된 {@link SensorDataMapping} 인스턴스
     */
    public static SensorDataMapping ofNewSensorDataType(Sensor sensor, DataType dataType, SensorStatus sensorStatus) {
        return new SensorDataMapping(
                sensor,
                dataType,
                sensorStatus
        );
    }

    /**
     * 센서의 상태를 갱신합니다.
     *
     * @param sensorStatus 새로 설정할 센서 상태
     */
    public void updateStatus(SensorStatus sensorStatus) {
        this.sensorStatus = sensorStatus;
    }
}
