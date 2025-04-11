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
}
