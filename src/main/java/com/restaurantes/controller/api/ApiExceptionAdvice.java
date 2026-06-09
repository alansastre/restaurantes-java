package com.restaurantes.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.restaurantes.controller.api")
public class ApiExceptionAdvice {

// personalizar los mensajes de error a nivel global
// traducir excepciones técnicas que dan 500 a un status correcto
// y mensaje amigable
//    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Map<String, Object>> handleError(Exception ex, HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Ocurrió un error.");
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", LocalDateTime.now());
        response.put("path", request.getRequestURI());

        return ResponseEntity.badRequest().body(response);
    }
}
