package com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.repositories;

import com.aquamonitor.platform.pc27737u202114790.monitoring.application.exceptions.DuplicateSamplingPlanCodeException;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlan;
import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.repositories.SamplingPlanRepository;
import com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.entities.SampleVolumeEmbeddable;
import com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.entities.SamplingPlanEntity;
import com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.entities.SamplingWindowEmbeddable;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

/**
 * JPA adapter for the sampling plan domain repository.
 */
@Repository
public class SamplingPlanRepositoryAdapter implements SamplingPlanRepository {

    private static final String PLAN_CODE_CONSTRAINT = "uk_sampling_plans_plan_code";

    private final SamplingPlanJpaRepository jpaRepository;

    public SamplingPlanRepositoryAdapter(SamplingPlanJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsByPlanCode(String planCode) {
        return jpaRepository.existsByPlanCode(planCode);
    }

    @Override
    public long save(SamplingPlan samplingPlan) {
        var entity = new SamplingPlanEntity(
                samplingPlan.getPlanCode(),
                samplingPlan.getStationId(),
                new SamplingWindowEmbeddable(
                        samplingPlan.getSamplingWindow().startDate(),
                        samplingPlan.getSamplingWindow().endDate()),
                new SampleVolumeEmbeddable(
                        samplingPlan.getSampleVolume().quantity(),
                        samplingPlan.getSampleVolume().unit()),
                samplingPlan.getPriorityLevel(),
                samplingPlan.getStatus(),
                samplingPlan.getSamplingNotes());

        try {
            return jpaRepository.saveAndFlush(entity).getId();
        } catch (DataIntegrityViolationException exception) {
            if (isPlanCodeConflict(exception)) {
                throw new DuplicateSamplingPlanCodeException(exception);
            }
            throw exception;
        }
    }

    private boolean isPlanCodeConflict(Throwable exception) {
        for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
            if (cause instanceof ConstraintViolationException violation
                    && PLAN_CODE_CONSTRAINT.equalsIgnoreCase(violation.getConstraintName())) {
                return true;
            }
        }
        return false;
    }
}
