package com.jvalencia.peliculas.model;

// ### Se agregan dependenias de validación
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
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

    // ### Definir los atributos de la entindad y sus validaciones para cada uno de ellos ###

    @Id
    @NotNull(message="El Id del registro no puede ser nulo.")
    private Long id;

    @NotNull(message="El nombre de la película no puede ser nulo.")
    @NotBlank(message = "El nombre de la película es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre de la película debe tener entre 2 y 150 caracteres.")
    private String titulo;

    @NotNull(message="El año de la película no puede ser nulo.")
    @Min(value = 1800, message = "El año debe ser mayor a 1800.")
    @Max(value = 2025, message = "El año debe ser menor a 2025.")
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
    @Size(min = 3, max = 150, message = "la sinopsis de la película debe tener entre 2 y 150 caracteres.")
    private String sinopsis;

}
