package com.movie_catalog.movieapp.service;

import com.movie_catalog.movieapp.model.Movie;
import com.movie_catalog.movieapp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;


    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        return movieRepository.search(keyword.trim());
    }

    public long count() {
        return movieRepository.count();
    }
}
