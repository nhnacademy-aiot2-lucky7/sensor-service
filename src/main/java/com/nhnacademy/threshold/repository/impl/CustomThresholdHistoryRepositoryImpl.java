package com.nhnacademy.threshold.repository.impl;

import com.nhnacademy.sensor.domain.QSensor;
import com.nhnacademy.sensor_type_mapping.domain.QSensorDataMapping;
import com.nhnacademy.threshold.domain.QThresholdHistory;
import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.repository.CustomThresholdHistoryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Slf4j
public class CustomThresholdHistoryRepositoryImpl extends QuerydslRepositorySupport implements CustomThresholdHistoryRepository {

    private final JPAQueryFactory queryFactory;

    private final QThresholdHistory qThresholdHistory;

    private final QSensorDataMapping qSensorDataMapping;

    private final QSensor qSensor;

    public CustomThresholdHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ThresholdHistory.class);
        this.queryFactory = queryFactory;
        this.qThresholdHistory = QThresholdHistory.thresholdHistory;
        this.qSensorDataMapping = QSensorDataMapping.sensorDataMapping;
        this.qSensor = QSensor.sensor;
    }

    @Override
    public List<RuleEngineResponse> findLatestThresholdSummariesByGatewayId(String gatewayId) {
        QThresholdHistory qThresholdHistorySub = new QThresholdHistory("qThresholdHistorySub");

        return queryFactory
                .select(
                        Projections.constructor(
                                RuleEngineResponse.class,
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensorDataMapping.dataType.dataTypeEnName,
                                qThresholdHistory.thresholdMin,
                                qThresholdHistory.thresholdMax,
                                qThresholdHistory.thresholdAvg,
                                qThresholdHistory.avgRangeMin,
                                qThresholdHistory.avgRangeMax,
                                qThresholdHistory.calculatedAt
                        )
                )
                .from(qThresholdHistory)
                .innerJoin(qThresholdHistory.sensorDataMapping, qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .where(qSensor.gatewayId.eq(gatewayId)
                        .and(
                                qThresholdHistory.calculatedAt.eq(
                                        JPAExpressions
                                                .select(qThresholdHistorySub.calculatedAt.max())
                                                .from(qThresholdHistorySub)
                                                .where(qThresholdHistorySub.sensorDataMapping.eq(qSensorDataMapping))
                                )
                        )
                )
                .fetch();
    }
}
