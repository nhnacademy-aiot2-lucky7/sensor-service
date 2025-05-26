package com.nhnacademy.sensor.repository.impl;

import com.nhnacademy.sensor.domain.QSensor;
import com.nhnacademy.sensor.domain.Sensor;
import com.nhnacademy.sensor.dto.QSensorIndexResponse;
import com.nhnacademy.sensor.dto.QSensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorIndexResponse;
import com.nhnacademy.sensor.dto.SensorInfoResponse;
import com.nhnacademy.sensor.dto.SensorSearchRequest;
import com.nhnacademy.sensor.repository.CustomSensorRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CustomSensorRepositoryImpl extends QuerydslRepositorySupport implements CustomSensorRepository {

    private final JPAQueryFactory queryFactory;

    private final QSensor qSensor;

    public CustomSensorRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Sensor.class);
        this.queryFactory = queryFactory;
        this.qSensor = QSensor.sensor;
    }

    @Override
    public long countByConditions(SensorSearchRequest request) {
        Long count = queryFactory
                .select(qSensor.sensorNo.count())
                .from(qSensor)
                .where(getBooleanBuilder(request))
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public boolean existsByConditions(SensorSearchRequest request) {
        return countByConditions(request) > 0L;
    }

    @Override
    public List<SensorInfoResponse> findByConditions(SensorSearchRequest request) {
        return queryFactory
                .select(
                        new QSensorInfoResponse(
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensor.sensorLocation,
                                qSensor.sensorSpot
                        )
                )
                .from(qSensor)
                .where(getBooleanBuilder(request))
                .fetch();
    }

    @Override
    public Set<SensorIndexResponse> findAllSensorUniqueKeys() {
        List<SensorIndexResponse> uniqueList =
                queryFactory
                        .select(
                                new QSensorIndexResponse(
                                        qSensor.gatewayId,
                                        qSensor.sensorId
                                )
                        )
                        .from(qSensor)
                        .fetch();

        Set<SensorIndexResponse> uniqueSet = new HashSet<>(uniqueList);
        if (uniqueList.size() != uniqueSet.size()) {
            log.warn("Sensor 테이블에 중복된 (gatewayId, sensorId) 조합이 존재할 수 있습니다. 전체 조회 건수: {}, 중복 제거 후 건수: {}", uniqueList.size(), uniqueSet.size());
        }
        return uniqueSet;
    }

    private BooleanBuilder getBooleanBuilder(SensorSearchRequest request) {
        BooleanBuilder builder = new BooleanBuilder();
        if (request.isNotNullGatewayId()) {
            builder.and(qSensor.gatewayId.eq(request.getGatewayId()));
        }
        if (request.isNotNullSensorId()) {
            builder.and(qSensor.sensorId.eq(request.getSensorId()));
        }
        if (request.isNotNullSensorLocation()) {
            builder.and(qSensor.sensorLocation.eq(request.getSensorLocation()));
        }
        if (request.isNotNullSensorSpot()) {
            builder.and(qSensor.sensorSpot.eq(request.getSensorSpot()));
        }
        return builder;
    }
}
