package com.nhnacademy.sensor_type_mapping.repository.impl;


import com.nhnacademy.sensor.domain.QSensor;
import com.nhnacademy.sensor.dto.QSensorInfoResponse;
import com.nhnacademy.sensor_type_mapping.domain.QSensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;
import com.nhnacademy.sensor_type_mapping.dto.QSearchNoResponse;
import com.nhnacademy.sensor_type_mapping.dto.QSensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.QSensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.QSensorDataMappingResponse;
import com.nhnacademy.sensor_type_mapping.dto.QSensorDataMappingWebResponse;
import com.nhnacademy.sensor_type_mapping.dto.SearchNoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingIndexResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingSearchRequest;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingWebResponse;
import com.nhnacademy.sensor_type_mapping.repository.CustomSensorDataMappingRepository;
import com.nhnacademy.type.domain.QDataType;
import com.nhnacademy.type.dto.QDataTypeInfoResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CustomSensorDataMappingRepositoryImpl extends QuerydslRepositorySupport implements CustomSensorDataMappingRepository {

    private final JPAQueryFactory queryFactory;

    private final QSensorDataMapping qSensorDataMapping;

    private final QSensor qSensor;

    private final QDataType qDataType;

    public CustomSensorDataMappingRepositoryImpl(JPAQueryFactory queryFactory) {
        super(SensorDataMapping.class);
        this.queryFactory = queryFactory;
        this.qSensorDataMapping = QSensorDataMapping.sensorDataMapping;
        this.qSensor = QSensor.sensor;
        this.qDataType = QDataType.dataType;
    }

    @Override
    public long countByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName) {
        Long count = queryFactory
                .select(qSensorDataMapping.sensorDataNo.count())
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qDataType.dataTypeEnName.eq(dataTypeEnName))
                )
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public boolean existsByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName) {
        return countByGatewayIdAndSensorIdAndDataTypeEnName(gatewayId, sensorId, dataTypeEnName) > 0L;
    }

    @Override
    public SensorDataMapping findByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName) {
        return queryFactory
                .select(qSensorDataMapping)
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qDataType.dataTypeEnName.eq(dataTypeEnName))
                )
                .fetchOne();
    }

    @Override
    public long countByConditions(SensorDataMappingSearchRequest request) {
        Long count = queryFactory
                .select(qSensor.sensorNo.count())
                .from(qSensor)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .where(getBooleanBuilder(request))
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public boolean existsByConditions(SensorDataMappingSearchRequest request) {
        return countByConditions(request) > 0L;
    }

    @Override
    public List<SensorDataMappingResponse> findByConditions(SensorDataMappingSearchRequest request) {
        return queryFactory
                .select(
                        Projections.constructor(
                                SensorDataMappingResponse.class,
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensor.sensorLocation,
                                qSensor.sensorSpot,
                                qSensorDataMapping.sensorStatus,
                                qDataType.dataTypeEnName
                        )
                )
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(getBooleanBuilder(request))
                .fetch();
    }

    @Override
    public SearchNoResponse findNoResponseByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName) {
        return queryFactory
                .select(
                        new QSearchNoResponse(
                                qSensorDataMapping.sensorDataNo
                        )
                )
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qDataType.dataTypeEnName.eq(dataTypeEnName))
                )
                .fetchOne();
    }

    @Override
    public SensorDataMappingResponse findInfoResponseByGatewayIdAndSensorIdAndDataTypeEnName(long gatewayId, String sensorId, String dataTypeEnName) {
        return queryFactory
                .select(
                        new QSensorDataMappingResponse(
                                new QSensorInfoResponse(
                                        qSensor.gatewayId,
                                        qSensor.sensorId,
                                        qSensor.sensorLocation,
                                        qSensor.sensorSpot
                                ),
                                new QDataTypeInfoResponse(
                                        qDataType.dataTypeEnName,
                                        qDataType.dataTypeKrName
                                ),
                                qSensorDataMapping.sensorStatus
                        )
                )
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qDataType.dataTypeEnName.eq(dataTypeEnName))
                )
                .fetchOne();
    }

    @Override
    public List<SensorDataMappingWebResponse> findAllWebResponseByGatewayId(long gatewayId) {
        return queryFactory
                .select(
                        new QSensorDataMappingWebResponse(
                                qSensor.sensorNo,
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qDataType.dataTypeEnName,
                                qSensor.sensorLocation,
                                qSensor.sensorSpot
                        )
                )
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(qSensor.gatewayId.eq(gatewayId))
                .fetch();
    }

    @Override
    public List<SensorDataMappingAiResponse> findAllAiResponsesByGatewayId(long gatewayId) {
        return queryFactory
                .select(
                        new QSensorDataMappingAiResponse(
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensorDataMapping.sensorStatus,
                                qDataType.dataTypeEnName
                        )
                )
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(qSensor.gatewayId.eq(gatewayId))
                .fetch();
    }

    @Override
    public List<SensorDataMappingAiResponse> findAllAiResponsesBySensorStatuses(List<SensorStatus> sensorStatuses) {
        return queryFactory
                .select(
                        new QSensorDataMappingAiResponse(
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensorDataMapping.sensorStatus,
                                qDataType.dataTypeEnName
                        )
                )
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(getSearchStatuses(sensorStatuses))
                .fetch();
    }

    @Override
    public Set<SensorDataMappingIndexResponse> findAllSensorDataUniqueKeys() {
        List<SensorDataMappingIndexResponse> uniqueList =
                queryFactory
                        .select(
                                new QSensorDataMappingIndexResponse(
                                        qSensor.gatewayId,
                                        qSensor.sensorId,
                                        qDataType.dataTypeEnName
                                )
                        )
                        .from(qSensorDataMapping)
                        .innerJoin(qSensorDataMapping.sensor, qSensor)
                        .innerJoin(qSensorDataMapping.dataType, qDataType)
                        .fetch();
        Set<SensorDataMappingIndexResponse> uniqueSet = new HashSet<>(uniqueList);
        if (uniqueList.size() != uniqueSet.size()) {
            log.warn("Sensor 테이블에 중복된 (gatewayId, sensorId, dataTypeEnName) 조합이 존재할 수 있습니다. 전체 조회 건수: {}, 중복 제거 후 건수: {}", uniqueList.size(), uniqueSet.size());
        }
        return uniqueSet;
    }

    private BooleanBuilder getSearchStatuses(List<SensorStatus> sensorStatuses) {
        BooleanBuilder builder = new BooleanBuilder();
        for (SensorStatus sensorStatus : sensorStatuses) {
            builder.or(qSensorDataMapping.sensorStatus.eq(sensorStatus));
        }
        return builder;
    }

    private BooleanBuilder getBooleanBuilder(SensorDataMappingSearchRequest request) {
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
        if (request.isNotNullDataTypeEnName()) {
            builder.and(qSensorDataMapping.dataType.dataTypeEnName.eq(request.getDataTypeEnName()));
        }
        if (request.isNotNullSensorStatus()) {
            builder.and(qSensorDataMapping.sensorStatus.eq(request.getSensorStatus()));
        }
        return builder;
    }
}
