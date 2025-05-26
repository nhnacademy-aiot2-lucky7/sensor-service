package com.nhnacademy.threshold.repository;

import com.nhnacademy.threshold.domain.ThresholdHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThresholdHistoryRepository extends JpaRepository<ThresholdHistory, Long>, CustomThresholdHistoryRepository {

}
