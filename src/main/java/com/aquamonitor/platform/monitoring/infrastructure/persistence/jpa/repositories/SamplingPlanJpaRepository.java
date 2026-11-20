package com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.repositories;

import com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.entities.SamplingPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for sampling plan persistence entities.
 */
public interface SamplingPlanJpaRepository extends JpaRepository<SamplingPlanEntity, Long> {

    boolean existsByPlanCode(String planCode);
}
