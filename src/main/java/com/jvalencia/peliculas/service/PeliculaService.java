package com.jvalencia.peliculas.service;
import com.jvalencia.peliculas.model.Pelicula;
import com.jvalencia.peliculas.repository.PeliculaRepository;
import com.jvalencia.peliculas.exception.PeliculaNotFoundException;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PeliculaService {
    @Autowired
    private PeliculaRepository repositorio;

    public List<Pelicula>traePeliculas(){
        return repositorio.findAll();
    }

    public Pelicula buscarPorId(Long id){
        return repositorio.findById(id)
            .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    public Pelicula insertar(Pelicula pelicula){
        if(repositorio.existsById(pelicula.getId())){
            throw new IllegalArgumentException("Ya existe una pelìcula en nuestra base de datos con ese id: " + pelicula.getId());
        }
        return repositorio.save(pelicula);
    }

    public Pelicula actualizar(Long id, Pelicula peliculaUpdated){
        Pelicula existente = repositorio.findById(id).orElseThrow(() -> new PeliculaNotFoundException(id));

        existente.setTitulo(peliculaUpdated.getTitulo());
        existente.setAnio(peliculaUpdated.getAnio());
        existente.setDirector(peliculaUpdated.getDirector());
        existente.setGenero(peliculaUpdated.getGenero());
        existente.setSinopsis(peliculaUpdated.getSinopsis());

        return repositorio.save(existente);
    }

    public void deleted(Long id){
        Pelicula existente = repositorio.findById(id).orElseThrow(()-> new PeliculaNotFoundException(id));

        repositorio.delete(existente);
    }
}
