package com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest.resources;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Request body for creating a sampling plan. The generated id is intentionally omitted.
 *
 * @param planCode unique sampling plan code
 * @param stationId monitoring station UUID in canonical form
 * @param samplingWindow planned start and end dates
 * @param sampleVolume planned sample quantity and unit
 * @param priorityLevel sampling priority from 1 through 5
 * @param status optional lifecycle status
 * @param samplingNotes optional notes
 */
@NullMarked
public record CreateSamplingPlanRequest(
        @NotBlank(message = "{samplingPlan.planCode.required}") String planCode,
        @NotBlank(message = "{samplingPlan.stationId.required}")
        @Pattern(
                regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "{samplingPlan.stationId.invalid}") String stationId,
        @NotNull(message = "{samplingPlan.samplingWindow.required}") @Valid SamplingWindowRequest samplingWindow,
        @NotNull(message = "{samplingPlan.sampleVolume.required}") @Valid SampleVolumeRequest sampleVolume,
        @NotNull(message = "{samplingPlan.priorityLevel.required}")
        @Min(value = 1, message = "{samplingPlan.priorityLevel.min}")
        @Max(value = 5, message = "{samplingPlan.priorityLevel.max}") BigInteger priorityLevel,
        @Nullable @Pattern(
                regexp = "PENDING|APPROVED|COMPLETED|CANCELLED",
                message = "{samplingPlan.status.invalid}") String status,
        @Nullable String samplingNotes) {

    /**
     * Request representation of a sampling window using ISO-8601 local dates.
     */
    @NullMarked
    public record SamplingWindowRequest(
            @NotBlank(message = "{samplingPlan.startDate.required}") String startDate,
            @NotBlank(message = "{samplingPlan.endDate.required}") String endDate) {

        /**
         * Checks that both dates are valid ISO-8601 calendar dates.
         *
         * @return true when both dates are valid or a required field is missing
         */
        @AssertTrue(message = "{samplingPlan.samplingWindow.iso}")
        public boolean isIsoDateRange() {
            if (startDate == null || startDate.isBlank() || endDate == null || endDate.isBlank()) {
                return true;
            }
            try {
                LocalDate.parse(startDate);
                LocalDate.parse(endDate);
                return true;
            } catch (DateTimeException exception) {
                return false;
            }
        }

        /**
         * Checks that the sampling window starts on or before it ends.
         *
         * @return true when the dates are ordered or cannot yet be compared
         */
        @AssertTrue(message = "{samplingPlan.samplingWindow.order}")
        public boolean isDateOrderValid() {
            if (!isIsoDateRange()) {
                return true;
            }
            if (startDate == null || startDate.isBlank() || endDate == null || endDate.isBlank()) {
                return true;
            }
            return !LocalDate.parse(startDate).isAfter(LocalDate.parse(endDate));
        }
    }

    /**
     * Request representation of the quantity and unit to sample.
     */
    @NullMarked
    public record SampleVolumeRequest(
            @NotNull(message = "{samplingPlan.quantity.required}")
            @Positive(message = "{samplingPlan.quantity.positive}")
            @Max(value = Integer.MAX_VALUE, message = "{samplingPlan.quantity.max}") BigInteger quantity,
            @NotBlank(message = "{samplingPlan.unit.required}") String unit) {
    }
}
