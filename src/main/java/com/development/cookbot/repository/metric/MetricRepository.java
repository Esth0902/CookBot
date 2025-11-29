package com.development.cookbot.repository.metric;

import com.development.cookbot.entity.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<MetricEntity, Long> {
}
