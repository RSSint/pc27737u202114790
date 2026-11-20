package com.aquamonitor.platform.pc27737u202114790.monitoring.domain.repositories;

import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlan;
import org.jspecify.annotations.NullMarked;

/**
 * Persistence port for sampling plans.
 */
@NullMarked
public interface SamplingPlanRepository {

    boolean existsByPlanCode(String planCode);

    long save(SamplingPlan samplingPlan);
}
