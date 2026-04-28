package com.jobela.jobela_api.common.exception;

import java.time.LocalDateTime;
import java.util.*;

import com.jobela.jobela_api.common.error.ErrorResponse;
import com.jobela.jobela_api.common.error.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authorization.AuthorizationDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        log.warn("User not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("User already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex, HttpServletRequest request) {
        log.warn("Invalid password operation: {}", ex.getMessage());

        return badRequest(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateCertificationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidateCertificationAlreadyExists(
            CandidateCertificationAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("Candidate certification already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateCertificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidateCertificationNotFound(
            CandidateCertificationNotFoundException ex, HttpServletRequest request) {
        log.warn("Candidate certification not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateDocumentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidateDocumentationAlreadyExists(
            CandidateDocumentAlreadyExistsException ex, HttpServletRequest request
    ) {
        log.warn("Candidate document already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateDocumentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidateDocumentNotFound(
            CandidateDocumentNotFoundException ex, HttpServletRequest request
    ) {
        log.warn("Candidate document not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateEducationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidateEducationAlreadyExists(
            CandidateEducationAlreadyExistsException ex, HttpServletRequest request
    ) {
        log.warn("Candidate education already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateEducationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidateEducationNotFound(
            CandidateEducationNotFoundException ex, HttpServletRequest request
    ) {
        log.warn("Candidate education not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateLocationPreferenceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidateLocationPreferenceNotFound(
            CandidateLocationPreferenceNotFoundException ex, HttpServletRequest request
    ) {
        log.warn("Candidate location preference not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidatePreferenceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidatePreferenceAlreadyExists(
            CandidatePreferenceAlreadyExistsException ex, HttpServletRequest request
    ) {
        log.warn("Candidate preference already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidatePreferenceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidatePreferenceNotFound(
            CandidatePreferenceNotFoundException ex, HttpServletRequest request
    ) {
        log.warn("Candidate preference not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateWorkAuthorizationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidateWorkAuthorizationAlreadyExists(
            CandidateWorkAuthorizationAlreadyExistsException ex, HttpServletRequest request
    ) {
        log.warn("Candidate work authorization already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateWorkAuthorizationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidateWorkAuthorizationNotFound(
            CandidateWorkAuthorizationNotFoundException ex, HttpServletRequest request
    ) {

        log.warn("Candidate work authorization not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateWorkExperienceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidateWorkExperienceAlreadyExistsException(
            CandidateWorkExperienceAlreadyExistsException ex, HttpServletRequest request
    ) {
        log.warn("Candidate work experience already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateWorkExperienceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidateWorkExperienceNotFoundException(
            CandidateWorkExperienceNotFoundException ex, HttpServletRequest request
    ) {
        log.warn("Candidate work experience not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateLanguageAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidateLanguageAlreadyExists(CandidateLanguageAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("Candidate language already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }


    @ExceptionHandler(CandidateLanguageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidateLanguageNotFound(CandidateLanguageNotFoundException ex, HttpServletRequest request) {
        log.warn("Candidate language not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCandidateAlreadyExists(CandidateAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("Candidate already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCandidateNotFound(CandidateNotFoundException ex, HttpServletRequest request) {
        log.warn("Candidate not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(CandidateSkillAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSkillAlreadyExists(CandidateSkillAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("Skill already exists: {}", ex.getMessage());

        return conflict(ex.getMessage(), request);
    }

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSkillNotFound(SkillNotFoundException ex, HttpServletRequest request) {
        log.warn("Skill not found: {}", ex.getMessage());

        return notFound(ex.getMessage(), request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        log.warn("Bad request: {}", ex.getMessage());

        return badRequest(ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        var validationErrorResponse = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResponse);
    }

    @ExceptionHandler(InvalidPaginationParameterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPaginationParameter(
            InvalidPaginationParameterException ex, HttpServletRequest request
    ) {
        log.warn("Invalid pagination or sorting parameter: {}", ex.getMessage());

        return badRequest(ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error occurred", ex);

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "Unexpected server error",
                request
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(
            AuthorizationDeniedException ex,
            HttpServletRequest request
    ) {
        var error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "Access denied",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status, String error, String message, HttpServletRequest request
    ) {
        var errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> notFound(String message, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", message, request);
    }

    private ResponseEntity<ErrorResponse> conflict(String message, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Conflict", message, request);
    }

    private ResponseEntity<ErrorResponse> badRequest(String message, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", message, request);
    }
}
