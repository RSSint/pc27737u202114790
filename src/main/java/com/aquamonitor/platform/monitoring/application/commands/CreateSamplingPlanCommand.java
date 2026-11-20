package com.aquamonitor.platform.pc27737u202114790.monitoring.application.commands;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

/**
 * Application command for creating a sampling plan.
 *
 * @param planCode unique sampling plan code
 * @param stationId station UUID string
 * @param startDate ISO-8601 sampling start date
 * @param endDate ISO-8601 sampling end date
 * @param sampleVolumeQuantity requested integer volume
 * @param sampleVolumeUnit volume unit
 * @param priorityLevel priority from 1 through 5
 * @param status optional status name
 * @param samplingNotes optional notes
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
