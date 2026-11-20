package com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JPA representation of a sampling plan's volume and unit.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleVolumeEmbeddable {

    @Column(name = "sample_volume_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "sample_volume_unit", nullable = false)
    private String unit;

    public SampleVolumeEmbeddable(Integer quantity, String unit) {
        this.quantity = quantity;
        this.unit = unit;
    }
}
