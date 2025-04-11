package com.jvalencia.peliculas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jvalencia.peliculas.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long>{


}
