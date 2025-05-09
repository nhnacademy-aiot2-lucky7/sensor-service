package com.nhnacademy.sensor.repository.impl;

import com.nhnacademy.sensor.domain.QSensor;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.repository.CustomSensorRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.HashSet;
import java.util.Set;

public class CustomSensorRepositoryImpl extends QuerydslRepositorySupport implements CustomSensorRepository {

    private final JPAQueryFactory queryFactory;

    private final QSensor qSensor;

    public CustomSensorRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Sensor.class);
        this.queryFactory = queryFactory;
        this.qSensor = QSensor.sensor;
    }

    @Override
    public long countBySensorId(String sensorId) {
        Long count = queryFactory
                .select(qSensor.sensorNo.count())
                .from(qSensor)
                .where(qSensor.sensorId.eq(sensorId))
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public boolean existsBySensorId(String sensorId) {
        return countBySensorId(sensorId) > 0L;
    }

    @Override
    public SensorInfoResponse findBySensorId(String sensorId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                SensorInfoResponse.class,
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensor.sensorLocation,
                                qSensor.sensorSpot
                        )
                )
                .from(qSensor)
                .where(qSensor.sensorId.eq(sensorId))
                .fetchOne();
    }

    @Override
    public Set<String> findDistinctSensorIds() {
        return new HashSet<>(
                queryFactory
                        .select(qSensor.sensorId)
                        .distinct()
                        .from(qSensor)
                        .fetch()
        );
    }
}
