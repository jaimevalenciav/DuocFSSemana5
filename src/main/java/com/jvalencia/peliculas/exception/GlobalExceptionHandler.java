package com.jvalencia.peliculas.exception;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


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

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex){
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status",HttpStatus.BAD_REQUEST.value());
            body.put("error","Error de validación");

            String mensajes = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining("; "));

            body.put("message", mensajes);

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);            
        }
    
    //Excepcion que maneja errores cuando el JSON no tenga la estructura adecuada
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleJsonParseError(HttpMessageNotReadableException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamap", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Estructura de JSON mal formado");
        body.put("message", "Verificar estructura de JSON sea correcta.");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //Excepcion que maneja errores cuando la solicitud realizada no está definida en el proyecto
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Se ha realizado una solicitud no válida");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //Excepción que maneja errores que se producen cuando no se cumple la integridad del Json (Id duplicado, restricciones en la BD, etc)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Ha ocurrido una violación a la integridad");
        body.put("message",
        "No se pudo guardar los datos en la base de datos. Por favor, revise que los datos no se encuentren duplicados o inválidos.");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


    //Excepción que majera errores que no están definidos anteriormente, por ej: problemas en la red, no se ejecuta el motor de BD
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Error Interno");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
