package com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model;

import org.jspecify.annotations.NullMarked;

/**
 * Lifecycle status of a water sampling plan.
 */
@NullMarked
public enum SamplingPlanStatus {
    PENDING,
    APPROVED,
    COMPLETED,
    CANCELLED
}
