package com.nhnacademy.sensor.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

/**
 * 센서 정보를 나타내는 JPA 엔티티입니다. <br>
 * 각 센서는 고유의 센서 ID와 게이트웨이 ID를 가지며, 설치 위치에 대한 정보도 포함합니다. <br>
 * 동일한 게이트웨이 내에서 센서 ID는 유일해야 합니다.
 *
 * <p><b>테이블명:</b> {@code sensors}</p>
 * <p><b>인덱스:</b> {@code uk_gateway_id_and_sensor_id} (게이트웨이 ID + 센서 ID 조합의 유일성 보장)</p>
 */
@Entity
@Table(
        name = "sensors",
        indexes = @Index(
                name = "uk_gateway_id_and_sensor_id",
                columnList = "gateway_id, sensor_id",
                unique = true
        )
)
@Getter
@ToString
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_no", nullable = false)
    @Comment("센서_번호")
    private Integer sensorNo;

    @Column(name = "gateway_id", length = 50, nullable = false, updatable = false)
    @Comment("게이트웨이_아이디")
    private String gatewayId;

    @Column(name = "sensor_id", length = 50, nullable = false, updatable = false)
    @Comment("센서_아이디")
    private String sensorId;

    @Column(name = "sensor_location", length = 30, nullable = true)
    @Comment("센서_설치_장소")
    private String sensorLocation;

    @Column(name = "sensor_spot", length = 30, nullable = true)
    @Comment("센서_설치_상세위치")
    private String sensorSpot;

    /**
     * JPA 전용 기본 생성자입니다.
     */
    protected Sensor() {
    }

    /**
     * 센서의 모든 주요 정보를 기반으로 새로운 센서 엔티티를 생성합니다. <br>
     * 외부에서는 직접 호출하지 않고, 정적 팩토리 메서드를 통해 인스턴스를 생성하십시오.
     *
     * @param gatewayId      게이트웨이 ID
     * @param sensorId       센서 ID
     * @param sensorLocation 센서 설치 장소
     * @param sensorSpot     센서 상세 설치 위치
     * @see Sensor#ofNewSensor(String, String, String, String)
     */
    private Sensor(String gatewayId, String sensorId, String sensorLocation, String sensorSpot) {
        this.gatewayId = gatewayId;
        this.sensorId = sensorId;
        this.sensorLocation = sensorLocation;
        this.sensorSpot = sensorSpot;
    }

    /**
     * <b>정적 팩토리 메서드입니다.</b>
     * <hr>
     * 주어진 센서의 게이트웨이 ID, 센서 ID, 설치 장소 및 상세 위치 정보를 모두 포함하는 새로운 센서를 생성합니다.
     *
     * @param gatewayId      게이트웨이 ID
     * @param sensorId       센서 ID
     * @param sensorLocation 센서 설치 장소 (예: "서버실 1번 룸")
     * @param sensorSpot     센서 상세 설치 위치 (예: "천장 왼쪽 구석")
     * @return 새로 생성된 {@link Sensor} 인스턴스
     */
    public static Sensor ofNewSensor(String gatewayId, String sensorId, String sensorLocation, String sensorSpot) {
        return new Sensor(
                gatewayId,
                sensorId,
                sensorLocation,
                sensorSpot
        );
    }

    /**
     * 센서의 설치 위치와 상세 위치를 변경합니다.
     *
     * @param sensorLocation 새로 설정할 센서 설치 장소
     * @param sensorSpot     새로 설정할 센서 상세 설치 위치
     */
    public void updateSensorPosition(String sensorLocation, String sensorSpot) {
        if (sensorLocation != null) {
            this.sensorLocation = sensorLocation;
        }
        if (sensorSpot != null) {
            this.sensorSpot = sensorSpot;
        }
    }
}
