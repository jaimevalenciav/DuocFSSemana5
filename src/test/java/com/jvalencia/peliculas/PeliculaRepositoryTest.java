package com.jvalencia.peliculas;

import com.jvalencia.peliculas.model.Pelicula;
import com.jvalencia.peliculas.repository.PeliculaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class PeliculaRepositoryTest {
    @Autowired
    private PeliculaRepository peliculaRepository;

    @Test
    public void testGuardarBuscar(){
        Pelicula pelicula = new Pelicula(7L, "El Padrino", 1972, "Coppola", "Crimen", "Familia de gansters" );

        peliculaRepository.save(pelicula);
        Optional<Pelicula> encontrada = peliculaRepository.findById(7L);
        assertTrue(encontrada.isPresent());
        assertEquals("El Padrino", encontrada.get().getTitulo());
    }

}
