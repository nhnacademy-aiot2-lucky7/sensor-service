package com.nhnacademy.sensor_type_mapping.repository.impl;


import com.nhnacademy.sensor.domain.QSensor;
import com.nhnacademy.sensor_type_mapping.domain.QSensorDataMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorDataMapping;
import com.nhnacademy.sensor_type_mapping.dto.QSensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingAiResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingFrontResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingInfoResponse;
import com.nhnacademy.sensor_type_mapping.dto.SensorDataMappingSearchRequest;
import com.nhnacademy.sensor_type_mapping.repository.CustomSensorDataMappingRepository;
import com.nhnacademy.type.domain.QDataType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

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

    @Override
    public long countByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName) {
        Long count = queryFactory
                .select(qSensorDataMapping.sensorDataNo.count())
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qSensorDataMapping.dataType.dataTypeEnName.eq(dataTypeEnName))
                )
                .fetchOne();
        return count != null ? count : 0L;
    }

    @Override
    public boolean existsByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName) {
        return countByGatewayIdAndSensorIdAndDataTypeEnName(gatewayId, sensorId, dataTypeEnName) > 0L;
    }

    @Override
    public SensorDataMapping findByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName) {
        return queryFactory
                .select(qSensorDataMapping)
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qSensorDataMapping.dataType.dataTypeEnName.eq(dataTypeEnName))
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
    public List<SensorDataMappingInfoResponse> findByConditions(SensorDataMappingSearchRequest request) {
        return queryFactory
                .select(
                        Projections.constructor(
                                SensorDataMappingInfoResponse.class,
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensor.sensorLocation,
                                qSensor.sensorSpot,
                                qSensorDataMapping.sensorStatus,
                                qSensorDataMapping.dataType.dataTypeEnName
                        )
                )
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .where(getBooleanBuilder(request))
                .fetch();
    }

    public List<SensorDataMappingAiResponse> findAllAiResponsesByGatewayId(String gatewayId) {
        return queryFactory
                .select(
                        new QSensorDataMappingAiResponse(
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensorDataMapping.sensorStatus,
                                qSensorDataMapping.dataType.dataTypeEnName
                        )
                )
                .from()
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .where(qSensor.gatewayId.eq(gatewayId))
                .fetch();
    }

    @Override
    public List<SensorDataMappingFrontResponse> findMappingInfoBySensorId(String sensorId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                SensorDataMappingFrontResponse.class,
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensor.sensorLocation,
                                qSensor.sensorSpot,
                                qSensorDataMapping.sensorStatus,
                                qDataType.dataTypeEnName,
                                qDataType.dataTypeKrName
                        )
                )
                .from(qSensorDataMapping)
                .innerJoin(qSensorDataMapping.sensor, qSensor)
                .innerJoin(qSensorDataMapping.dataType, qDataType)
                .where(qSensor.sensorId.eq(sensorId))
                .fetch();
    }
}
