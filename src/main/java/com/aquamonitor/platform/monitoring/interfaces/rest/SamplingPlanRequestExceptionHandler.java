package com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest;

import com.aquamonitor.platform.pc27737u202114790.shared.application.result.ApplicationError;
import com.aquamonitor.platform.pc27737u202114790.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Maps sampling plan request parsing and validation errors to HTTP 400 responses.
 */
@RestControllerAdvice(assignableTypes = SamplingPlansController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SamplingPlanRequestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException exception) {
        String details = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
                .reduce((first, second) -> first + "; " + second)
                .orElse("Request validation failed.");
        return badRequest(details);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleUnreadableBody(HttpMessageNotReadableException exception) {
        return badRequest("Request body is malformed or contains an invalid value.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception exception) {
        var error = ApplicationError.unexpected(
                "sampling-plans",
                "An unexpected error occurred while creating the sampling plan.");
        ProblemDetail problemDetail = ProblemDetailFromApplicationErrorAssembler
                .toProblemDetailFromApplicationError(error);
        problemDetail.setProperty("message", problemDetail.getDetail());
        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }

    private ResponseEntity<ProblemDetail> badRequest(String details) {
        var error = ApplicationError.validationError("request-body", details);
        ProblemDetail problemDetail = ProblemDetailFromApplicationErrorAssembler
                .toProblemDetailFromApplicationError(error);
        problemDetail.setProperty("message", problemDetail.getDetail());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
