package com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest.resources;

import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlanStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;

/**
 * Request body for creating a sampling plan. The generated id is intentionally omitted.
 */
@NullMarked
public record CreateSamplingPlanRequest(
        @NotBlank String planCode,
        @NotBlank String stationId,
        @NotNull @Valid SamplingWindowRequest samplingWindow,
        @NotNull @Valid SampleVolumeRequest sampleVolume,
        @NotNull @Min(1) @Max(5) BigInteger priorityLevel,
        @Nullable String status,
        @Nullable String samplingNotes) {

    /**
     * Request representation of a sampling window using ISO-8601 local dates.
     */
    @NullMarked
    public record SamplingWindowRequest(
            @NotBlank String startDate,
            @NotBlank String endDate) {
    }

    /**
     * Request representation of the quantity and unit to sample.
     */
    @NullMarked
    public record SampleVolumeRequest(
            @NotNull @Positive BigInteger quantity,
            @NotBlank String unit) {
    }
}
