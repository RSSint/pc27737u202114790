package com.aquamonitor.platform.pc27737u202114790.monitoring.domain.model.valueobjects;

import org.jspecify.annotations.NullMarked;

/**
 * Quantity and unit of water to collect for a sampling plan.
 */
@NullMarked
public record SampleVolume(Integer quantity, String unit) {

    public SampleVolume {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero.");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit is required.");
        }
    }

    /**
     * Returns a new sample volume with the requested quantity added.
     *
     * @param additionalVolume positive quantity to add
     * @return a new sample volume with the same unit
     */
    public SampleVolume addVolume(Integer additionalVolume) {
        if (additionalVolume == null || additionalVolume <= 0) {
            throw new IllegalArgumentException("additionalVolume must be greater than zero.");
        }
        if (additionalVolume > Integer.MAX_VALUE - quantity) {
            throw new IllegalArgumentException("volume quantity exceeds the maximum integer value.");
        }
        return new SampleVolume(quantity + additionalVolume, unit);
    }
}
