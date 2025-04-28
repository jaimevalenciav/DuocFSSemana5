package com.jvalencia.peliculas;
import com.jvalencia.peliculas.controller.PeliculaController;
import com.jvalencia.peliculas.model.Pelicula;
import com.jvalencia.peliculas.service.PeliculaService;
import com.jvalencia.peliculas.hateoas.PeliculaModelAssembler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@WebMvcTest(PeliculaController.class)
public class PeliculaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean

    private PeliculaService peliculaService;
    @SuppressWarnings("removal")
    @MockBean
    private PeliculaModelAssembler peliculaModelAssembler;

    @Test
    @WithMockUser(username="jvalencia", password = "jvalencia123", roles = {"ADMIN"})

    public void testObtenerPorId() throws Exception{
        Pelicula pelicula = new Pelicula(1L, "Interestelar",2014, "Nolan", "Ciencia Ficción","Viaje al espacio" );

        EntityModel<Pelicula> peliculaModel = EntityModel.of(pelicula,
            linkTo(methodOn(PeliculaController.class).buscarPorId(1L)).withSelfRel(),
            linkTo(methodOn(PeliculaController.class).eliminarPelicula(1L)).withRel("delete"),
            linkTo(methodOn(PeliculaController.class).actualizar(1L, null)).withRel("update"),
            linkTo(methodOn(PeliculaController.class).traePeliculas()).withRel("all"));

        when(peliculaService.buscarPorId(1L)).thenReturn(pelicula);
        when(peliculaModelAssembler.toModel(pelicula)).thenReturn(peliculaModel);

        mockMvc.perform(get("/peliculas/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.titulo").value("Interestelar"))
            .andExpect(jsonPath("$.anio").value(2014))
            .andExpect(jsonPath("$.genero").value("Ciencia Ficción"))
            .andExpect(jsonPath("$._links.self.href").exists())
            .andExpect(jsonPath("$._links.delete.href").exists())
            .andExpect(jsonPath("$._links.update.href").exists())
            ;

    }
    
}
