package com.development.cookbot.repository.step;

import com.development.cookbot.entity.StepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<StepEntity, Long> {
}
