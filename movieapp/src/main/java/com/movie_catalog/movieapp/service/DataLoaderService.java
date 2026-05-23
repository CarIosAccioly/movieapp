package com.movie_catalog.movieapp.service;

import com.movie_catalog.movieapp.model.Movie;
import com.movie_catalog.movieapp.repository.MovieRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataLoaderService {

    private final MovieRepository movieRepository;

    public DataLoaderService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void loadData() {
        if (movieRepository.count() > 0) return; // already loaded

        try (Reader reader = new InputStreamReader(
                new ClassPathResource("data/imdb_top_1000.csv").getInputStream());
             CSVReader csv = new CSVReader(reader)) {

            List<Movie> batch = new ArrayList<>();
            String[] line;
            csv.readNext(); // skip header
            while ((line = csv.readNext()) != null) {
                try {
                    Movie m = Movie.builder()
                            .posterUrl(safe(line, 0))
                            .title(safe(line, 1))
                            .releaseYear(parseInt(safe(line, 2)))
                            .genre(safe(line, 5))
                            .rating(parseDouble(safe(line, 6)))
                            .overview(truncate(safe(line, 7)))
                            .director(safe(line, 9))
                            .build();
                    if (m.getTitle() != null && !m.getTitle().isBlank()) {
                        batch.add(m);
                    }
                } catch (Exception ignored) {}
                if (batch.size() >= 100) {
                    movieRepository.saveAll(batch);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) movieRepository.saveAll(batch);
            System.out.println(">> Dataset loaded: " + movieRepository.count() + " movies");
        } catch (Exception e) {
            System.err.println("Could not load dataset: " + e.getMessage());
        }
    }

    private String safe(String[] arr, int idx) {
        return idx < arr.length && arr[idx] != null ? arr[idx].trim() : "";
    }
    private Integer parseInt(String s) {
        try { return Integer.parseInt(s.replaceAll("[^0-9]", "")); } catch (Exception e) { return 2000; }
    }
    private @NotNull(message = "Rating is required") @DecimalMin(value = "0.0", message = "Rating must be at least 0") @DecimalMax(value = "10.0", message = "Rating  must be at least 0") Float parseDouble(String s) {
        try { return (float) Double.parseDouble(s); } catch (Exception e) { return 0.0F; }
    }
    private String truncate(String s) {
        return s == null ? "" : (s.length() > 1000 ? s.substring(0, 1000) : s);
    }
}
