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
    private final PeliculaRepository repositorio;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.repositorio = peliculaRepository;
    }

    public List<Pelicula>traePeliculas(){
        return repositorio.findAll();
    }

    public Pelicula buscarPorId(Long id){
        return repositorio.findById(id)
            .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    public Pelicula insertar(Pelicula pelicula){
        //valida si existe la película
        if(repositorio.existsById(pelicula.getId())){
            //si existe devuelve respuesta de que existe la pelicula con el id 
            throw new IllegalArgumentException("Ya existe una pelìcula en nuestra base de datos con ese id: " + pelicula.getId());
        }
        // Si la película no existe se inserta el registro
        return repositorio.save(pelicula);
    }


    public Pelicula actualizar(Long id, Pelicula peliculaUpdated){
        // Valida primero si la película existe, si no lo encuentra lanza la excepcion con el id
        Pelicula existente = repositorio.findById(id).orElseThrow(() -> new PeliculaNotFoundException(id));

        // Si la pelicula existe se toman los datos que vienen del Json para entregarla al método que actualiza
        existente.setTitulo(peliculaUpdated.getTitulo());
        existente.setAnio(peliculaUpdated.getAnio());
        existente.setDirector(peliculaUpdated.getDirector());
        existente.setGenero(peliculaUpdated.getGenero());
        existente.setSinopsis(peliculaUpdated.getSinopsis());

        // se pelicula existe se realiza la actualizaciòn del registro
        return repositorio.save(existente);
    }

    public void deleted(Long id){
        // Valida que el Id existe, si no lo encuentra se lanza la excepción con el id
        Pelicula existente = repositorio.findById(id).orElseThrow(()-> new PeliculaNotFoundException(id));
        // Si existe se elimina el registro
        repositorio.delete(existente);
    }
}
