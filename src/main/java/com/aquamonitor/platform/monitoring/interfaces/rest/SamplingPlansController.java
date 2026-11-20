package com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest;

import com.aquamonitor.platform.pc27737u202114790.monitoring.application.commands.CreateSamplingPlanCommand;
import com.aquamonitor.platform.pc27737u202114790.monitoring.application.services.CreateSamplingPlanService;
import com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest.resources.CreateSamplingPlanRequest;
import com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest.resources.SamplingPlanResource;
import com.aquamonitor.platform.pc27737u202114790.shared.interfaces.rest.transform.ResponseEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

/**
 * REST endpoint for sampling plan creation.
 */
@RestController
@RequestMapping("/api/v1/sampling-plans")
public class SamplingPlansController {

    private final CreateSamplingPlanService createSamplingPlanService;

    public SamplingPlansController(CreateSamplingPlanService createSamplingPlanService) {
        this.createSamplingPlanService = createSamplingPlanService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateSamplingPlanRequest request) {
        var command = new CreateSamplingPlanCommand(
                request.planCode(),
                request.stationId(),
                request.samplingWindow().startDate(),
                request.samplingWindow().endDate(),
                request.sampleVolume().quantity(),
                request.sampleVolume().unit(),
                request.priorityLevel(),
                request.status(),
                request.samplingNotes());

        var response = ResponseEntityAssembler.toResponseEntityFromResult(
                createSamplingPlanService.handle(command),
                SamplingPlanResource::from,
                HttpStatus.CREATED);

        if (response.getBody() instanceof ProblemDetail problemDetail) {
            problemDetail.setProperty("message", problemDetail.getDetail());
        }
        return response;
    }
}
