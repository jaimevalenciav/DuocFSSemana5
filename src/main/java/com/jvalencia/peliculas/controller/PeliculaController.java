package com.jvalencia.peliculas.controller;

import com.jvalencia.peliculas.hateoas.PeliculaModelAssembler;
import com.jvalencia.peliculas.model.Pelicula;
import com.jvalencia.peliculas.model.ResponseWrapper;
import com.jvalencia.peliculas.service.PeliculaService;

// Se agrega dependencia Valid para realizar las validaciones en los métodos que lo requieran
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.EntityModel;
import org.apache.catalina.connector.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaModelAssembler assembler;
    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService, PeliculaModelAssembler assembler, PeliculaModelAssembler peliculaModelAssembler){
        this.peliculaService = peliculaService;
        this.assembler  = assembler;
    }

    // ### Endpoint para traer todas las películas almacenadas en la base de datos ###
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pelicula>>>   traePeliculas(){
        log.info("GET /peliculas - Obteniendo todas las peliculas.");
        List<Pelicula> peliculas = peliculaService.traePeliculas();

        if(peliculas.isEmpty()){
            log.warn("No hay pelìculas registradas actualmente.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)            
            .body(CollectionModel.empty());
        }

        List<EntityModel<Pelicula>> modelos = peliculas.stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(modelos,
                linkTo(methodOn(PeliculaController.class).traePeliculas()).withSelfRel()));

        /*ResponseWrapper<Pelicula> respuesta = new ResponseWrapper<>(
            "OK",
            peliculas.size(),
            peliculas);
        return ResponseEntity.ok(respuesta);*/
    }
 
    
    // ### Endpoint para traer película por Id ###
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pelicula>> buscarPorId(@PathVariable Long id){
        log.info("GET /peliculas/{} - Buscando peliculas por ID", id);
        Pelicula pelicula = peliculaService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(pelicula));
    }

    // ### Endpoint para insertar una nueva película ###
    @PostMapping
    public ResponseEntity<EntityModel<Pelicula>> insertarNuevaPelicula(@Valid @RequestBody Pelicula pelicula){
        log.info("POST /peliculas - Creando película: {}", pelicula.getTitulo());

        Pelicula creada = peliculaService.insertar(pelicula);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(creada));

    }

    // ### Endpoint para actualizar una película por Id ###
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pelicula>> actualizar(@PathVariable Long id,
        @Valid @RequestBody Pelicula peliculaUpdated) {
    
        log.info("PUT /peliculas/{} - Actualizando película", id);
    
        Pelicula updated = peliculaService.actualizar(id, peliculaUpdated);
    
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    // ### Endpoint para eliminar una pelicula por Id ###
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> eliminarPelicula(@PathVariable Long id){
        log.warn("DELETE /peliculas/{} - Eliminando película", id);
        
        //Ejecuta el método eliminar entregándole id del registro
        peliculaService.deleted(id);

        // devuelve respuesta si se elimina el registro
        return ResponseEntity.ok(
            new ResponseWrapper<>(
                "Película eliminada exitosamente",
                0,
                null
            ));
    }
    

}
