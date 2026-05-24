package com.movie_catalog.movieapp.service;

import com.movie_catalog.movieapp.model.Movie;
import com.movie_catalog.movieapp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Business logic layer for movies; controllers call this instead of touching the repository directly.
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    // Constructor injection of the repository dependency.
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Return every movie in the database.
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    // Fetch a single movie by id, or throw if it doesn't exist.
    public Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));
    }

    // Create or update a movie.
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    // Remove a movie by id.
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    // Search by keyword; falls back to listing all movies when the keyword is empty.
    public List<Movie> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        return movieRepository.search(keyword.trim());
    }

    // Total number of movies (used on the home page).
    public long count() {
        return movieRepository.count();
    }
}
