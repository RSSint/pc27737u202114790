package com.aquamonitor.platform.pc27737u202114790.monitoring.application.services;

import com.aquamonitor.platform.pc27737u202114790.monitoring.application.commands.CreateSamplingPlanCommand;
import com.aquamonitor.platform.pc27737u202114790.monitoring.application.exceptions.DuplicateSamplingPlanCodeException;
import com.aquamonitor.platform.pc27737u202114790.monitoring.application.results.CreatedSamplingPlan;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlan;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlanStatus;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.valueobjects.SampleVolume;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.valueobjects.SamplingWindow;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.repositories.SamplingPlanRepository;
import com.aquamonitor.platform.pc27737u202114790.shared.application.result.ApplicationError;
import com.aquamonitor.platform.pc27737u202114790.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Creates and persists sampling plans.
 */
@Service
@NullMarked
public class CreateSamplingPlanService {

    private final SamplingPlanRepository samplingPlanRepository;

    public CreateSamplingPlanService(SamplingPlanRepository samplingPlanRepository) {
        this.samplingPlanRepository = samplingPlanRepository;
    }

    /**
     * Validates, checks uniqueness, and persists a sampling plan.
     *
     * @param command creation data
     * @return either the saved plan and id or a structured application error
     */
    public Result<CreatedSamplingPlan, ApplicationError> handle(CreateSamplingPlanCommand command) {
        try {
            SamplingPlan samplingPlan = toDomain(command);
            if (samplingPlanRepository.existsByPlanCode(samplingPlan.getPlanCode())) {
                return Result.failure(ApplicationError.conflict(
                        "SAMPLING_PLAN",
                        ""));
            }

            long id = samplingPlanRepository.save(samplingPlan);
            return Result.success(new CreatedSamplingPlan(id, samplingPlan));
        } catch (DuplicateSamplingPlanCodeException exception) {
            return Result.failure(ApplicationError.conflict(
                    "SAMPLING_PLAN",
                    ""));
        } catch (IllegalArgumentException | ArithmeticException | DateTimeException exception) {
            return Result.failure(ApplicationError.validationError(
                    "sampling-plan",
                    message("samplingPlan.validation.invalid")));
        }
    }

    private SamplingPlan toDomain(CreateSamplingPlanCommand command) {
        UUID stationId = parseStationId(command.stationId());
        LocalDate startDate = LocalDate.parse(command.startDate());
        LocalDate endDate = LocalDate.parse(command.endDate());
        int quantity = toInteger(command.sampleVolumeQuantity(), "sampleVolume.quantity");
        int priorityLevel = toInteger(command.priorityLevel(), "priorityLevel");
        SamplingPlanStatus status = parseStatus(command.status());

        return new SamplingPlan(
                command.planCode(),
                stationId,
                new SamplingWindow(startDate, endDate),
                new SampleVolume(quantity, command.sampleVolumeUnit()),
                priorityLevel,
                status,
                command.samplingNotes());
    }

    private UUID parseStationId(String value) {
        UUID stationId = UUID.fromString(value);
        if (!stationId.toString().equalsIgnoreCase(value)) {
            throw new IllegalArgumentException("stationId must be a valid UUID.");
        }
        return stationId;
    }

    private int toInteger(BigInteger value, String fieldName) {
        try {
            return value.intValueExact();
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException(fieldName + " must fit in a 32-bit integer.", exception);
        }
    }

    private SamplingPlanStatus parseStatus(String value) {
        return value == null ? null : SamplingPlanStatus.valueOf(value);
    }

    private String message(String key) {
        return ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale()).getString(key);
    }
}
