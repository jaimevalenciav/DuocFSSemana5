package com.jvalencia.peliculas.hateoas;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.jvalencia.peliculas.controller.PeliculaController;
import com.jvalencia.peliculas.model.Pelicula;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class PeliculaModelAssembler implements RepresentationModelAssembler<Pelicula, EntityModel<Pelicula>>{
    @Override
    public EntityModel<Pelicula> toModel(Pelicula pelicula){
        return EntityModel.of(
            pelicula,
            linkTo(methodOn(PeliculaController.class)
                .buscarPorId(pelicula.getId()))
                .withSelfRel(),
            
            linkTo(methodOn(PeliculaController.class)
                .eliminarPelicula(pelicula.getId()))
                .withRel("delete"),
            
            linkTo(methodOn(PeliculaController.class)
                .actualizar(pelicula.getId(), null))
                .withRel("update"),
            
            linkTo(methodOn(PeliculaController.class)
                .traePeliculas())
                .withRel("all"));
        
    }
}
