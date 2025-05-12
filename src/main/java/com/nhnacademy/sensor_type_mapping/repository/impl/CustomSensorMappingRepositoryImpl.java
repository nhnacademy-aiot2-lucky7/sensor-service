package com.nhnacademy.sensor_type_mapping.repository.impl;


import com.nhnacademy.sensor.domain.QSensor;
import com.nhnacademy.sensor_type_mapping.domain.QSensorMapping;
import com.nhnacademy.sensor_type_mapping.domain.SensorMapping;
import com.nhnacademy.sensor_type_mapping.dto.SensorMappingFrontResponse;
import com.nhnacademy.sensor_type_mapping.repository.CustomSensorMappingRepository;
import com.nhnacademy.type.domain.QDataType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomSensorMappingRepositoryImpl extends QuerydslRepositorySupport implements CustomSensorMappingRepository {

    private final JPAQueryFactory queryFactory;

    private final QSensorMapping qSensorMapping;

    private final QSensor qSensor;

    private final QDataType qDataType;

    public CustomSensorMappingRepositoryImpl(JPAQueryFactory queryFactory) {
        super(SensorMapping.class);
        this.queryFactory = queryFactory;
        this.qSensorMapping = QSensorMapping.sensorMapping;
        this.qSensor = QSensor.sensor;
        this.qDataType = QDataType.dataType;
    }

    /**
     * 센서 상태를 업데이트 하기 위해서, 만든 Entity 조회 Method 입니다.
     */
    @Override
    public SensorMapping findByGatewayIdAndSensorIdAndDataTypeEnName(String gatewayId, String sensorId, String dataTypeEnName) {
        return queryFactory
                .select(qSensorMapping)
                .from(qSensorMapping)
                .innerJoin(qSensorMapping.sensor, qSensor)
                .innerJoin(qSensorMapping.dataType, qDataType)
                .where(
                        qSensor.gatewayId.eq(gatewayId)
                                .and(qSensor.sensorId.eq(sensorId))
                                .and(qDataType.dataTypeEnName.eq(dataTypeEnName))
                )
                .fetchOne();
    }

    @Override
    public List<SensorMappingFrontResponse> findMappingInfoBySensorId(String sensorId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                SensorMappingFrontResponse.class,
                                qSensor.gatewayId,
                                qSensor.sensorId,
                                qSensorMapping.sensorStatus,
                                qSensor.sensorLocation,
                                qSensor.sensorSpot,
                                qDataType.dataTypeEnName,
                                qDataType.dataTypeKrName
                        )
                )
                .from(qSensorMapping)
                .innerJoin(qSensorMapping.sensor, qSensor)
                .innerJoin(qSensorMapping.dataType, qDataType)
                .where(qSensor.sensorId.eq(sensorId))
                .fetch();
    }
}
