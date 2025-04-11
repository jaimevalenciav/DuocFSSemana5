package com.jvalencia.peliculas.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PeliculaNotFoundException.class)
    public ResponseEntity<Object> handlePeliculaNotFound(PeliculaNotFoundException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error","Not Found");
        body.put("message",ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
