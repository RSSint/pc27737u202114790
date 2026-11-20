package com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest.resources;

import com.aquamonitor.platform.pc27737u202114790.monitoring.application.results.CreatedSamplingPlan;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlan;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlanStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.format.DateTimeFormatter;

/**
 * Created sampling plan returned by the REST API.
 */
@NullMarked
public record SamplingPlanResource(
        Long id,
        String planCode,
        String stationId,
        SamplingWindowResource samplingWindow,
        SampleVolumeResource sampleVolume,
        Integer priorityLevel,
        SamplingPlanStatus status,
        @Nullable String samplingNotes) {

    public static SamplingPlanResource from(CreatedSamplingPlan created) {
        SamplingPlan plan = created.samplingPlan();
        return new SamplingPlanResource(
                created.id(),
                plan.getPlanCode(),
                plan.getStationId().toString(),
                new SamplingWindowResource(
                        plan.getSamplingWindow().startDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        plan.getSamplingWindow().endDate().format(DateTimeFormatter.ISO_LOCAL_DATE)),
                new SampleVolumeResource(
                        plan.getSampleVolume().quantity(),
                        plan.getSampleVolume().unit()),
                plan.getPriorityLevel(),
                plan.getStatus(),
                plan.getSamplingNotes());
    }

    public record SamplingWindowResource(String startDate, String endDate) {
    }

    public record SampleVolumeResource(Integer quantity, String unit) {
    }
}
