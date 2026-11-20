package com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * JPA representation of a sampling plan's date window.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SamplingWindowEmbeddable {

    @Column(name = "sampling_window_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "sampling_window_end_date", nullable = false)
    private LocalDate endDate;

    public SamplingWindowEmbeddable(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
