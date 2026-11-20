package com.aquamonitor.platform.pc27737u202114790.monitoring.application.commands;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

/**
 * Application command for creating a sampling plan.
 */
@NullMarked
public record CreateSamplingPlanCommand(
        String planCode,
        String stationId,
        String startDate,
        String endDate,
        BigInteger sampleVolumeQuantity,
        String sampleVolumeUnit,
        BigInteger priorityLevel,
        @Nullable String status,
        @Nullable String samplingNotes) {
}
