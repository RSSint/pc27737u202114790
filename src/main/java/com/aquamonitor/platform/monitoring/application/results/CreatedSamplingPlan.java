package com.aquamonitor.platform.pc27737u202114790.monitoring.application.results;

import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlan;
import org.jspecify.annotations.NullMarked;

/**
 * Newly persisted sampling plan and its generated identifier.
 */
@NullMarked
public record CreatedSamplingPlan(Long id, SamplingPlan samplingPlan) {
}
