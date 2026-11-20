package com.aquamonitor.platform.pc27737u202114790.monitoring.domain.repositories;

import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlan;
import org.jspecify.annotations.NullMarked;

/**
 * Persistence port for sampling plans.
 */
@NullMarked
public interface SamplingPlanRepository {

    /**
     * Checks whether a plan code is already stored.
     *
     * @param planCode code to check
     * @return true when a plan with this code exists
     */
    boolean existsByPlanCode(String planCode);

    /**
     * Persists a sampling plan.
     *
     * @param samplingPlan aggregate to persist
     * @return generated database identifier
     */
    long save(SamplingPlan samplingPlan);
}
