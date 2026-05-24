package com.movie_catalog.movieapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA entity mapped to the "movies" table; validation annotations enforce field rules on form input.
@Entity
@Table(name = "movies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    // Primary key, auto-incremented by the database.
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    // Movie title; required, max 200 characters.
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    // Year of release; required and constrained to a sensible range (1888 is film's earliest year).
    @NotNull(message = "Release year is required")
    @Min(value = 1888, message = "Year must be 1888 or latter")
    @Max(value = 2100, message = "Year cannot exceed 2100")
    private Integer releaseYear;

    // Movie genre; required.
    @NotBlank(message = "Genre is required")
    @Size(max = 200)
    private String genre;

    // Director's name; required.
    @NotBlank(message = "Director is required")
    @Size(max = 100)
    private String director;

    // Score from 0.0 to 10.0; required.
    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message =  "Rating must be at least 0")
    @DecimalMax(value = "10.0", message = "Rating  must be at least 0")
    private Float rating;

    // Short plot summary; optional, max 1000 characters.
    @Size(max = 1000)
    @Column(length = 1000)
    private String overview;

    // Link to the poster image; optional.
    @Size(max = 500)
    private String posterUrl;
}
