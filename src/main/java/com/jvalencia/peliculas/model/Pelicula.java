package com.jvalencia.peliculas.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="peliculas")

public class Pelicula {

    @Id
    @NotNull(message="El Id del registro no puede ser nulo.")
    private Long id;

    @NotNull(message="El nombre de la película no puede ser nulo.")
    @NotBlank(message = "El nombre de la película es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre de la película debe tener entre 2 y 150 caracteres.")
    private String titulo;

    @NotNull(message="El año de la película no puede ser nulo.")
    @NotBlank(message = "El año de la película es obligatorio.")
    @Size(min = 4, max = 4, message = "El año de la película debe tener 4 caracteres.")
    private int anio;

    @NotNull(message="El nombre del director no puede ser nulo.")
    @NotBlank(message = "El nombre del director es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre del director debe tener entre 2 y 150 caracteres.")
    private String director;

    @NotNull(message="El género de la película no puede ser nulo.")
    @NotBlank(message = "El género de la película es obligatorio.")
    @Size(min = 3, max = 100, message = "El género de la película debe tener entre 2 y 150 caracteres.")
    private String genero;

    @NotNull(message="La sinopsis de la película no puede ser nulo.")
    @NotBlank(message = "La sinopsis de la película es obligatorio.")
    @Size(min = 3, max = 100, message = "la sinopsis de la película debe tener entre 2 y 150 caracteres.")
    private String sinopsis;

}
