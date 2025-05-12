package com.nhnacademy.type.repository;

import com.nhnacademy.type.domain.DataType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataTypeRepository extends JpaRepository<DataType, String>, CustomDataTypeRepository {

}
