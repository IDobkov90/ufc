package com.example.ufc.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 * Catches exceptions thrown by controllers and provides user-friendly error pages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Attribute constants
    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_ERROR_CODE = "errorCode";
    private static final String ATTR_TIMESTAMP = "timestamp";
    private static final String ATTR_REQUESTED_URL = "requestedUrl";

    /**
     * Handle UserAlreadyExistsException
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExists(UserAlreadyExistsException ex, Model model) {
        logger.warn("User already exists: {}", ex.getMessage());
        model.addAttribute(ATTR_ERROR_MESSAGE, ex.getMessage());
        return "register";
    }

    /**
     * Handle ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model, HttpServletRequest request) {
        logger.warn("Resource not found: {} - URL: {}", ex.getMessage(), request.getRequestURI());
        model.addAttribute(ATTR_ERROR_MESSAGE, ex.getMessage());
        model.addAttribute(ATTR_ERROR_CODE, HttpStatus.NOT_FOUND.value());
        model.addAttribute(ATTR_TIMESTAMP, LocalDateTime.now());
        return "error/404";
    }

    /**
     * Handle Access Denied Exception
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, Model model, HttpServletRequest request) {
        logger.warn("Access denied: {} - URL: {}", ex.getMessage(), request.getRequestURI());
        model.addAttribute(ATTR_ERROR_MESSAGE, "Нямате права за достъп до този ресурс.");
        model.addAttribute(ATTR_ERROR_CODE, HttpStatus.FORBIDDEN.value());
        model.addAttribute(ATTR_TIMESTAMP, LocalDateTime.now());
        return "error/403";
    }

    /**
     * Handle 404 - Page Not Found
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoHandlerFoundException ex, Model model, HttpServletRequest request) {
        logger.warn("Page not found: {}", request.getRequestURI());
        model.addAttribute(ATTR_ERROR_MESSAGE, "Страницата не беше намерена.");
        model.addAttribute(ATTR_ERROR_CODE, HttpStatus.NOT_FOUND.value());
        model.addAttribute(ATTR_REQUESTED_URL, request.getRequestURI());
        model.addAttribute(ATTR_TIMESTAMP, LocalDateTime.now());
        return "error/404";
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model, HttpServletRequest request) {
        logger.warn("Illegal argument: {} - URL: {}", ex.getMessage(), request.getRequestURI());
        model.addAttribute(ATTR_ERROR_MESSAGE, "Невалидни данни: " + ex.getMessage());
        model.addAttribute(ATTR_ERROR_CODE, HttpStatus.BAD_REQUEST.value());
        model.addAttribute(ATTR_TIMESTAMP, LocalDateTime.now());
        return "error/400";
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model, HttpServletRequest request) {
        logger.error("Unexpected error occurred: {} - URL: {}", ex.getMessage(), request.getRequestURI(), ex);
        model.addAttribute(ATTR_ERROR_MESSAGE, "Възникна грешка при обработката на вашата заявка.");
        model.addAttribute(ATTR_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute(ATTR_TIMESTAMP, LocalDateTime.now());
        return "error/500";
    }
}
