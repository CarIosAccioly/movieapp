package com.movie_catalog.movieapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotNull(message = "Release year is required")
    @Min(value = 1888, message = "Year must be 1888 or latter")
    @Max(value = 2100, message = "Year cannot exceed 2100")
    private Integer releaseYear;

    @NotBlank(message = "Genre is required")
    @Size(max = 200)
    private String genre;

    @NotBlank(message = "Director is required")
    @Size(max = 100)
    private String director;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message =  "Rating must be at least 0")
    @DecimalMax(value = "10.0", message = "Rating  must be at least 0")
    private Float rating;

    @Size(max = 1000)
    @Column(length = 1000)
    private String overview;

    @Size(max = 500)
    private String posterUrl;
}
