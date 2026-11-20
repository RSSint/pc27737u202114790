package com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest.resources;

import com.aquamonitor.platform.pc27737u202114790.monitoring.application.results.CreatedSamplingPlan;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlan;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlanStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.format.DateTimeFormatter;

/**
 * Created sampling plan returned by the REST API.
 *
 * @param id generated database identifier
 * @param planCode plan code
 * @param stationId station UUID in canonical form
 * @param samplingWindow sampling date window formatted as ISO-8601 strings
 * @param sampleVolume sample quantity and unit
 * @param priorityLevel sampling priority
 * @param status lifecycle status
 * @param samplingNotes optional notes
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

    /**
     * Maps an application result to the HTTP response resource.
     *
     * @param created persisted plan and generated id
     * @return REST representation of the created plan
     */
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

    /**
     * ISO-8601 date representation for the response.
     *
     * @param startDate inclusive window start date
     * @param endDate inclusive window end date
     */
    public record SamplingWindowResource(String startDate, String endDate) {
    }

    /**
     * Sample quantity and measurement unit in the response.
     *
     * @param quantity number of volume units
     * @param unit volume unit
     */
    public record SampleVolumeResource(Integer quantity, String unit) {
    }
}
