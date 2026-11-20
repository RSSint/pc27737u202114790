package com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.valueobjects;

import java.time.LocalDate;

/**
 * Date interval during which a sampling plan is carried out.
 */
public record SamplingWindow(LocalDate startDate, LocalDate endDate) {
}
