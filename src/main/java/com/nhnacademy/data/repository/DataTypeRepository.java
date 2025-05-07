package com.nhnacademy.data.repository;

import com.nhnacademy.data.domain.DataType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataTypeRepository extends JpaRepository<DataType, Integer>, CustomDataTypeRepository {

}
