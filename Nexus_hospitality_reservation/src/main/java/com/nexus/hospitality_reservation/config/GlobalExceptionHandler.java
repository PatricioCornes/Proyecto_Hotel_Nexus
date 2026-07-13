package com.nexus.hospitality_reservation.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiError> malformed(HttpMessageNotReadableException ex, HttpServletRequest req) { return response(HttpStatus.BAD_REQUEST, "MALFORMED_JSON", "El cuerpo JSON no es válido", req, Map.of()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String,String> details = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
                e -> e.getField(), e -> Objects.requireNonNullElse(e.getDefaultMessage(), "Valor inválido"), (a,b) -> a));
        return response(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "La solicitud contiene datos inválidos", req, details);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiError> constraint(ConstraintViolationException ex, HttpServletRequest req) {
        return response(HttpStatus.BAD_REQUEST, "CONSTRAINT_VIOLATION", ex.getMessage(), req, Map.of());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ApiError> notFound(IllegalArgumentException ex, HttpServletRequest req) {
        return response(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", ex.getMessage(), req, Map.of());
    }
    @ExceptionHandler({IllegalStateException.class, DataIntegrityViolationException.class})
    ResponseEntity<ApiError> conflict(Exception ex, HttpServletRequest req) {
        return response(HttpStatus.CONFLICT, "BUSINESS_CONFLICT", ex.getMessage(), req, Map.of());
    }
    @ExceptionHandler(FeignException.class)
    ResponseEntity<ApiError> unavailable(FeignException ex, HttpServletRequest req) {
        return response(HttpStatus.SERVICE_UNAVAILABLE, "DEPENDENCY_UNAVAILABLE", "Un microservicio requerido no está disponible", req, Map.of());
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> unexpected(Exception ex, HttpServletRequest req) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Ocurrió un error interno", req, Map.of());
    }
    private ResponseEntity<ApiError> response(HttpStatus status, String code, String message, HttpServletRequest req, Map<String,String> details) {
        return ResponseEntity.status(status).body(new ApiError(OffsetDateTime.now(), status.value(), status.getReasonPhrase(), code, message, req.getRequestURI(), details));
    }
    public record ApiError(OffsetDateTime timestamp, int status, String error, String codigo, String mensaje, String path, Map<String,String> detalles) {}
}
