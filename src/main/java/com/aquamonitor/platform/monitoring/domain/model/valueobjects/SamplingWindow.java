package com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.valueobjects;

import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;

/**
 * Date interval during which a sampling plan is carried out.
 */
@NullMarked
public record SamplingWindow(LocalDate startDate, LocalDate endDate) {

    public SamplingWindow {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate is required.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("endDate is required.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must not be after endDate.");
        }
    }

    /**
     * Checks whether a date falls within this interval, including both endpoints.
     *
     * @param checkDate date to check
     * @return true when the date is within this interval
     */
    public boolean contains(LocalDate checkDate) {
        if (checkDate == null) {
            throw new IllegalArgumentException("checkDate is required.");
        }
        return !checkDate.isBefore(startDate) && !checkDate.isAfter(endDate);
    }
}
