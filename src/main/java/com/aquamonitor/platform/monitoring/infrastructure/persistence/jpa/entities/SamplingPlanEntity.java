package com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.entities;

import com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.SamplingPlanStatus;
import com.aquamonitor.platform.pc27737u202114790.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * JPA persistence entity for a water sampling plan.
 */
@Entity
@Table(
        name = "sampling_plans",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_sampling_plans_plan_code",
                columnNames = "plan_code"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SamplingPlanEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "plan_code", nullable = false)
    private String planCode;

    @Column(nullable = false)
    private UUID stationId;

    @Embedded
    private SamplingWindowEmbeddable samplingWindow;

    @Embedded
    private SampleVolumeEmbeddable sampleVolume;

    @Column(nullable = false)
    private Integer priorityLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SamplingPlanStatus status;

    private String samplingNotes;

    public SamplingPlanEntity(
            String planCode,
            UUID stationId,
            SamplingWindowEmbeddable samplingWindow,
            SampleVolumeEmbeddable sampleVolume,
            Integer priorityLevel,
            SamplingPlanStatus status,
            String samplingNotes) {
        this.planCode = planCode;
        this.stationId = stationId;
        this.samplingWindow = samplingWindow;
        this.sampleVolume = sampleVolume;
        this.priorityLevel = priorityLevel;
        this.status = status;
        this.samplingNotes = samplingNotes;
    }
}
