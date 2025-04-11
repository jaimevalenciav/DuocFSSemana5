package com.jvalencia.peliculas.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class PeliculaNotFoundException extends RuntimeException{

    public PeliculaNotFoundException(Long id){
        super("No se ha encontrado en nuestra base de datos la película buscada con el id: " + id);
    }

}
