package com.spsolutions.newsweb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Centralised exception handling for the whole web layer.
 *
 * <p>Scoped to the {@code web} package so it handles the Thymeleaf (HTML) controllers only;
 * the REST API has its own JSON handler ({@code ApiExceptionHandler}).</p>
 *
 * <p>SOLID notes:</p>
 * <ul>
 *   <li><b>SRP</b> — controllers focus on the happy path; translating exceptions into
 *       user-facing error pages lives here and nowhere else.</li>
 *   <li><b>OCP</b> — new exception types are supported by adding a new {@code @ExceptionHandler}
 *       method, without editing existing controllers.</li>
 *   <li><b>DRY</b> — one place decides the HTTP status and view for each failure class.</li>
 * </ul>
 */
@ControllerAdvice(basePackages = "com.spsolutions.newsweb.web")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** 404 — a requested category or news item does not exist. */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        log.warn("Resource not found: {}", ex.getMessage());
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("title", "Page not found");
        model.addAttribute("message", ex.getMessage());
        return "error/error";
    }

    /** 400 — the request referenced a malformed value (e.g. a non-numeric id). */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(IllegalArgumentException ex, Model model) {
        log.warn("Bad request: {}", ex.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("title", "Invalid request");
        model.addAttribute("message", ex.getMessage());
        return "error/error";
    }

    /** 500 — anything not handled above. The real cause is logged, never shown to the user. */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUnexpected(Exception ex, Model model) {
        log.error("Unexpected error", ex);
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("title", "Something went wrong");
        model.addAttribute("message", "An unexpected error occurred. Please try again later.");
        return "error/error";
    }
}
