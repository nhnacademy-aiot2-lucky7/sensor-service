package com.nhnacademy.threshold.repository.impl;

import com.nhnacademy.sensor.domain.QSensor;
import com.nhnacademy.sensor_type_mapping.domain.QSensorDataMapping;
import com.nhnacademy.threshold.domain.QThresholdHistory;
import com.nhnacademy.threshold.domain.ThresholdHistory;
import com.nhnacademy.threshold.dto.QThresholdInfoResponse;
import com.nhnacademy.threshold.dto.RuleEngineResponse;
import com.nhnacademy.threshold.dto.ThresholdInfoResponse;
import com.nhnacademy.threshold.repository.CustomThresholdHistoryRepository;
import com.nhnacademy.type.domain.QDataType;
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

    private final QDataType qDataType;

    public CustomThresholdHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ThresholdHistory.class);
        this.queryFactory = queryFactory;
        this.qThresholdHistory = QThresholdHistory.thresholdHistory;
        this.qSensorDataMapping = QSensorDataMapping.sensorDataMapping;
        this.qSensor = QSensor.sensor;
        this.qDataType = QDataType.dataType;
    }

    @Override
    public List<RuleEngineResponse> findLatestThresholdSummariesByGatewayId(long gatewayId) {
        QThresholdHistory qThresholdHistorySub = new QThresholdHistory("qThresholdHistorySub");

        return queryFactory
                .select(
                        Projections.constructor(
                                RuleEngineResponse.class,
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qDataType.dataTypeEnName,
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
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
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

    @Override
    public List<ThresholdInfoResponse> findLatestThresholdInfoBySensor(
            long gatewayId, String sensorId
    ) {
        QThresholdHistory qThresholdHistorySub = new QThresholdHistory("qThresholdHistorySub");

        return queryFactory
                .select(
                        new QThresholdInfoResponse(
                                qThresholdHistory.thresholdMin,
                                qThresholdHistory.thresholdMax,
                                qThresholdHistory.thresholdAvg,
                                qDataType.dataTypeEnName
                        )
                )
                .from(qThresholdHistory)
                .innerJoin(qThresholdHistory.sensorDataMapping, qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qThresholdHistory.calculatedAt.eq(
                                        JPAExpressions
                                                .select(qThresholdHistorySub.calculatedAt.max())
                                                .from(qThresholdHistorySub)
                                                .where(qThresholdHistorySub.sensorDataMapping.eq(qThresholdHistory.sensorDataMapping))
                                ))
                )
                .fetch();
    }

    @Override
    public ThresholdInfoResponse findLatestThresholdInfoBySensorData(
            long gatewayId, String sensorId,
            String typeEnName
    ) {
        return queryFactory
                .select(
                        new QThresholdInfoResponse(
                                qThresholdHistory.thresholdMin,
                                qThresholdHistory.thresholdMax,
                                qThresholdHistory.thresholdAvg,
                                qDataType.dataTypeEnName
                        )
                )
                .from(qThresholdHistory)
                .innerJoin(qThresholdHistory.sensorDataMapping, qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qDataType.dataTypeEnName.eq(typeEnName))
                )
                .orderBy(qThresholdHistory.calculatedAt.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public List<ThresholdInfoResponse> findLatestThresholdInfoBySensorDataWithLimit(
            long gatewayId, String sensorId,
            String typeEnName, int limit
    ) {
        return queryFactory
                .select(
                        new QThresholdInfoResponse(
                                qThresholdHistory.thresholdMin,
                                qThresholdHistory.thresholdMax,
                                qThresholdHistory.thresholdAvg,
                                qDataType.dataTypeEnName
                        )
                )
                .from(qThresholdHistory)
                .innerJoin(qThresholdHistory.sensorDataMapping, qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qDataType.dataTypeEnName.eq(typeEnName))
                )
                .orderBy(qThresholdHistory.calculatedAt.desc())
                .limit(limit)
                .fetch();
    }
}
