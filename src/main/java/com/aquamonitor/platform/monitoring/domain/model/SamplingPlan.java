package com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model;

import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.valueobjects.SampleVolume;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.valueobjects.SamplingWindow;
import com.aquamonitor.platform.pc27737u202114790.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

/**
 * Domain aggregate representing a water sampling plan.
 */
@Getter
@NullMarked
public class SamplingPlan extends AbstractDomainAggregateRoot<SamplingPlan> {

    private final String planCode;
    private final UUID stationId;
    private final SamplingWindow samplingWindow;
    private final SampleVolume sampleVolume;
    private final Integer priorityLevel;
    private final SamplingPlanStatus status;
    private final @Nullable String samplingNotes;

    public SamplingPlan(
            String planCode,
            UUID stationId,
            SamplingWindow samplingWindow,
            SampleVolume sampleVolume,
            Integer priorityLevel,
            @Nullable SamplingPlanStatus status,
            @Nullable String samplingNotes) {
        if (planCode == null || planCode.isBlank()) {
            throw new IllegalArgumentException("planCode is required.");
        }
        if (stationId == null) {
            throw new IllegalArgumentException("stationId is required and must be a valid UUID.");
        }
        if (samplingWindow == null) {
            throw new IllegalArgumentException("samplingWindow is required.");
        }
        if (sampleVolume == null) {
            throw new IllegalArgumentException("sampleVolume is required.");
        }
        if (priorityLevel == null || priorityLevel < 1 || priorityLevel > 5) {
            throw new IllegalArgumentException("priorityLevel must be between 1 and 5.");
        }
        this.planCode = planCode;
        this.stationId = stationId;
        this.samplingWindow = samplingWindow;
        this.sampleVolume = sampleVolume;
        this.priorityLevel = priorityLevel;
        this.status = status == null ? SamplingPlanStatus.PENDING : status;
        this.samplingNotes = samplingNotes;
    }
}
