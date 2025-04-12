package com.jvalencia.peliculas.controller;

import com.jvalencia.peliculas.model.Pelicula;
import com.jvalencia.peliculas.model.ResponseWrapper;
import com.jvalencia.peliculas.service.PeliculaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService){
        this.peliculaService=peliculaService;
    }

    @GetMapping
    public ResponseEntity<?>traePeliculas(){
        List<Pelicula> peliculas = peliculaService.traePeliculas();

        if(peliculas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("En este momento no hay peliculas registradas en el sistema.");
        }

        ResponseWrapper<Pelicula> respuesta = new ResponseWrapper<>(
            "OK",
            peliculas.size(),
            peliculas);
        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/{id}")
    public Pelicula buscarPorId(@PathVariable Long id){
        return peliculaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Pelicula>> insertarNuevaPelicula(@Valid @RequestBody Pelicula pelicula){
        Pelicula insertada = peliculaService.insertar(pelicula);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("Pelicula guardada satisfactoriamente.", 1, List.of(insertada)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Pelicula>> actualizar(@PathVariable Long id,
        @Valid @RequestBody Pelicula peliculaUpdated){
            Pelicula updated = peliculaService.actualizar(id, peliculaUpdated);

            return ResponseEntity.ok(
                new ResponseWrapper<>("Pelicula se ha actualizado satisfactoriamente", 1, List.of(updated)));
            
            
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Pelicula>> eliminarPelicula(@PathVariable Long id){
        peliculaService.deleted(id);

        return ResponseEntity.ok(
            new ResponseWrapper<>("Pelicula eliminada satisfactoriamente",1, null)
        );
    }
    

}
