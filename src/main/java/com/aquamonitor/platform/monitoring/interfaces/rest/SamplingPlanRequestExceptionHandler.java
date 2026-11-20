package com.aquamonitor.platform.pc27737u202114790.monitoring.interfaces.rest;

import com.aquamonitor.platform.pc27737u202114790.shared.application.result.ApplicationError;
import com.aquamonitor.platform.pc27737u202114790.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ResourceBundle;

/**
 * Maps sampling plan request parsing and validation errors to HTTP 400 responses.
 */
@RestControllerAdvice(assignableTypes = SamplingPlansController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SamplingPlanRequestExceptionHandler {

    /**
     * Converts bean validation failures to a localized HTTP 400 response.
     *
     * @param exception validation failure
     * @return localized problem response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException exception) {
        String details = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
                .reduce((first, second) -> first + "; " + second)
                .orElse("Request validation failed.");
        return badRequest(details);
    }

    /**
     * Converts malformed JSON to a localized HTTP 400 response.
     *
     * @param exception message conversion failure
     * @return localized problem response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleUnreadableBody(HttpMessageNotReadableException exception) {
        return badRequest(message("samplingPlan.request.malformed"));
    }

    /**
     * Converts unexpected sampling plan endpoint errors to HTTP 500.
     *
     * @param exception unhandled endpoint error
     * @return localized generic error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception exception) {
        var error = ApplicationError.unexpected(
                "sampling-plans",
                "");
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

    private String message(String key) {
        return ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale()).getString(key);
    }
}
