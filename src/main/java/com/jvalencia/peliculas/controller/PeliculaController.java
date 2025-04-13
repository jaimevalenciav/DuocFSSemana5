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

    // ### Endpoint para traer todas las películas almacenadas en la base de datos ###
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
 
    // ### Endpoint para traer película por Id ###
    @GetMapping("/{id}")
    public Pelicula buscarPorId(@PathVariable Long id){
        return peliculaService.buscarPorId(id);
    }

    // ### Endpoint para insertar una nueva película ###
    @PostMapping
    public ResponseEntity<ResponseWrapper<Pelicula>> insertarNuevaPelicula(@Valid @RequestBody Pelicula pelicula){
        Pelicula insertada = peliculaService.insertar(pelicula);

        // devuelve respuesta registro creado
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("Pelicula guardada satisfactoriamente.", 1, List.of(insertada)));

    }

    // ### Endpoint para actualizar una película por Id ###
    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Pelicula>> actualizar(@PathVariable Long id,
        // @Valid le dice a St¡pring boot que valide los datos los atributdos de peliculaUpdated
        @Valid @RequestBody Pelicula peliculaUpdated){

            //Ejecuta el metodo actualizar y recibe el id y el Json del body
            Pelicula updated = peliculaService.actualizar(id, peliculaUpdated);

            // devuelve la respuesta si la perlícula se actualizó exitosamente
            return ResponseEntity.ok(
                new ResponseWrapper<>("Pelicula se ha actualizado satisfactoriamente", 1, List.of(updated)));      
            
    }

    // ### Endpoint para eliminar una pelicula por Id ###
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Pelicula>> eliminarPelicula(@PathVariable Long id){
        
        //Ejecuta el método eliminar entregándole id del registro
        peliculaService.deleted(id);

        // devuelve respuesta si se elimina el registro
        return ResponseEntity.ok(
            new ResponseWrapper<>("Pelicula eliminada satisfactoriamente",1, null)
        );
    }
    

}
