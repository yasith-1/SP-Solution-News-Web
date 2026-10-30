package com.spsolutions.newsweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Standard error response returned by the REST API")
public record ApiError(
        @Schema(example = "404") int status,
        @Schema(example = "Not Found") String error,
        @Schema(description = "Human-readable message") String message,
        @Schema(description = "Field-level validation errors, if any") Map<String, String> fieldErrors,
        Instant timestamp) {

    public static ApiError of(int status, String error, String message) {
        return new ApiError(status, error, message, null, Instant.now());
    }

    public static ApiError validation(int status, String error, String message, Map<String, String> fieldErrors) {
        return new ApiError(status, error, message, fieldErrors, Instant.now());
    }
}
