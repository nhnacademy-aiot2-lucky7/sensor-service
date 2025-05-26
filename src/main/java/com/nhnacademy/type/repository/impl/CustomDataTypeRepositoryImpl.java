package com.nhnacademy.type.repository.impl;

import com.nhnacademy.type.domain.DataType;
import com.nhnacademy.type.domain.QDataType;
import com.nhnacademy.type.repository.CustomDataTypeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Map;
import java.util.stream.Collectors;

public class CustomDataTypeRepositoryImpl extends QuerydslRepositorySupport implements CustomDataTypeRepository {

    private final JPAQueryFactory queryFactory;

    private final QDataType qDataType;

    public CustomDataTypeRepositoryImpl(JPAQueryFactory queryFactory) {
        super(DataType.class);
        this.queryFactory = queryFactory;
        this.qDataType = QDataType.dataType;
    }

    @Override
    public Map<String, String> findAllAsMap() {
        return queryFactory
                .selectFrom(qDataType)
                .fetch()
                .stream()
                .collect(
                        Collectors.toMap(
                                DataType::getDataTypeEnName,
                                DataType::getDataTypeKrName
                        )
                );
    }
}
