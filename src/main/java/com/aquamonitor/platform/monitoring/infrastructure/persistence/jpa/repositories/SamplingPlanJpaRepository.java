package com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.repositories;

import com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.entities.SamplingPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for sampling plan persistence entities.
 */
public interface SamplingPlanJpaRepository extends JpaRepository<SamplingPlanEntity, Long> {

    /**
     * Checks whether a plan code is already stored.
     *
     * @param planCode code to check
     * @return true when a matching entity exists
     */
    boolean existsByPlanCode(String planCode);
}
