package com.jvalencia.peliculas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jvalencia.peliculas.model.Pelicula;

// ### Creamos una interfaz para extender JpaRepository para acceder a los datos en la base de datos
public interface PeliculaRepository extends JpaRepository<Pelicula, Long>{

}
