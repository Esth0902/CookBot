package com.development.cookbot.repository.preference;

import com.development.cookbot.entity.PreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<PreferenceEntity, Long> {
}
