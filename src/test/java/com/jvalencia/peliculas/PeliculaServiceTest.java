package com.jvalencia.peliculas;

import com.jvalencia.peliculas.exception.PeliculaNotFoundException;
import com.jvalencia.peliculas.model.Pelicula;
import com.jvalencia.peliculas.repository.PeliculaRepository;
import com.jvalencia.peliculas.service.PeliculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // Asegura que se use la configuración de `application-test.properties`
public class PeliculaServiceTest {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PeliculaService peliculaService;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        peliculaRepository.deleteAll();
    }

    @Test
    public void testObtenerTodas() {
        // Preparar las entidades
        Pelicula p1 = new Pelicula(1L, "Pelicula 1", 2020, "Director1", "Drama", "Sinopsis 1");
        Pelicula p2 = new Pelicula(2L, "Pelicula 2", 2020, "Director2", "Drama", "Sinopsis 2");

        // Guardar en la base de datos
        peliculaRepository.save(p1);
        peliculaRepository.save(p2);

        // Llamar al servicio
        List<Pelicula> resultado = peliculaService.traePeliculas();

        // Verificar los resultados
        assertEquals(2, resultado.size()); // Se espera que haya 2 películas
        assertEquals("Pelicula 1", resultado.get(0).getTitulo()); // El título de la primera película debe ser "Pelicula 1"
    }

    @Test
    public void testObtenerPorId_existente() {
        // Preparar entidad
        Pelicula pelicula = new Pelicula(1L, "Pelicula Text", 2022, "Director Test", "Acción", "Sinopsis 1");

        // Guardar en la base de datos
        peliculaRepository.save(pelicula);

        // Buscar por ID
        Pelicula resultado = peliculaService.buscarPorId(1L);

        // Verificar el resultado
        assertEquals("Pelicula Text", resultado.getTitulo());
        assertEquals(2022, resultado.getAnio());
    }

    @Test
    public void testObtenerPorId_noExistente() {
        // Intentar buscar una película que no existe
        assertThrows(PeliculaNotFoundException.class, () -> peliculaService.buscarPorId(99L));

    };
}

