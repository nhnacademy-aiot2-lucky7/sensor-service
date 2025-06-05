package com.nhnacademy.threshold.domain;

import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "threshold_historys")
@Getter
@ToString
public class ThresholdHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "threshold_history_no", nullable = false)
    @Comment("임계치_내역_번호")
    private Long thresholdHistoryNo;


    @Column(name = "threshold_min", nullable = false)
    @Comment("최저_임계치")
    private Double thresholdMin;

    @Column(name = "threshold_max", nullable = false)
    @Comment("최고_임계치")
    private Double thresholdMax;

    @Column(name = "threshold_avg", nullable = false)
    @Comment("평균_임계치")
    private Double thresholdAvg;


    @Column(name = "min_range_min", nullable = false)
    @Comment("최저_임계치의_최소값")
    private Double minRangeMin;

    @Column(name = "min_range_max", nullable = false)
    @Comment("최저_임계치의_최대값")
    private Double minRangeMax;


    @Column(name = "max_range_min", nullable = false)
    @Comment("최고_임계치의_최소값")
    private Double maxRangeMin;

    @Column(name = "max_range_max", nullable = false)
    @Comment("최고_임계치의_최대값")
    private Double maxRangeMax;


    @Column(name = "avg_range_min", nullable = false)
    @Comment("평균_임계치의_최소값")
    private Double avgRangeMin;

    @Column(name = "avg_range_max", nullable = false)
    @Comment("평균_임계치의_최대값")
    private Double avgRangeMax;


    @Column(name = "min_diff", nullable = true)
    @Comment("이전_최저_임계값에_대한_변화율")
    private Double minDiff;

    @Column(name = "max_diff", nullable = true)
    @Comment("이전_최고_임계값에_대한_변화율")
    private Double maxDiff;

    @Column(name = "avg_diff", nullable = true)
    @Comment("이전_평균_임계값에_대한_변화율")
    private Double avgDiff;


    @Column(name = "data_count", nullable = false)
    @Comment("계산된_데이터_개수")
    private Integer dataCount;

    @Column(name = "calculated_at", nullable = false, updatable = false)
    @Comment("계산된_시간")
    private Long calculatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sensor_data_no", referencedColumnName = "sensor_data_no",
            nullable = false, updatable = false
    )
    @Comment("센서_데이터_번호")
    @ToString.Exclude
    private SensorDataMapping sensorDataMapping;

    /**
     * JPA 전용 기본 생성자입니다.
     */
    protected ThresholdHistory() {
    }

    public ThresholdHistory(
            Double thresholdMin, Double thresholdMax, Double thresholdAvg,
            Double minRangeMin, Double minRangeMax, Double maxRangeMin,
            Double maxRangeMax, Double avgRangeMin, Double avgRangeMax,
            Double minDiff, Double maxDiff, Double avgDiff,
            Integer dataCount, Long calculatedAt,
            SensorDataMapping sensorDataMapping
    ) {
        this.thresholdMin = thresholdMin;
        this.thresholdMax = thresholdMax;
        this.thresholdAvg = thresholdAvg;
        this.minRangeMin = minRangeMin;
        this.minRangeMax = minRangeMax;
        this.maxRangeMin = maxRangeMin;
        this.maxRangeMax = maxRangeMax;
        this.avgRangeMin = avgRangeMin;
        this.avgRangeMax = avgRangeMax;
        this.minDiff = minDiff;
        this.maxDiff = maxDiff;
        this.avgDiff = avgDiff;
        this.dataCount = dataCount;
        this.calculatedAt = calculatedAt;
        this.sensorDataMapping = sensorDataMapping;
    }
}
