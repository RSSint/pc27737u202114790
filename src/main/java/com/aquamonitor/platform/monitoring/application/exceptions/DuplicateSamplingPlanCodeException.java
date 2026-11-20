package com.aquamonitor.platform.pc27737u202114790.monitoring.application.exceptions;

/**
 * Raised when a sampling plan code conflicts with the database unique constraint.
 */
public class DuplicateSamplingPlanCodeException extends RuntimeException {

    public DuplicateSamplingPlanCodeException(Throwable cause) {
        super("A sampling plan with this planCode already exists.", cause);
    }
}
