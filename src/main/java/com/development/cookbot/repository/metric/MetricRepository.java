package com.development.cookbot.repository.metric;

import com.development.cookbot.entity.MetricEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface MetricRepository extends JpaRepository<MetricEntity, Long> {

    @Transactional
    @Query(value = "SELECT m FROM MetricEntity m WHERE m.user.id = ?1")
    List<MetricEntity> findAllMetricsByUserId(Long userId);

    @Transactional
    @Query(value = "SELECT m FROM MetricEntity m WHERE m.creationDate >= ?1 AND m.creationDate <= ?2")
    List<MetricEntity> getMetricsByRangeDate(Timestamp start, Timestamp end);
}
