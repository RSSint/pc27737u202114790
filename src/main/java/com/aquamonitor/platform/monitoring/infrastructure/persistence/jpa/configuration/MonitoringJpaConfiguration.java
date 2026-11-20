package com.aquamonitor.platform.pc27737u202114790.monitoring.infrastructure.persistence.jpa.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables auditing for monitoring persistence entities that extend the shared base entity.
 */
@Configuration
@EnableJpaAuditing
public class MonitoringJpaConfiguration {
}
