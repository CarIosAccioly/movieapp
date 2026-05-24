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

// Seeds the database with movies from a bundled CSV the first time the app starts.
@Service
public class DataLoaderService {

    private final MovieRepository movieRepository;

    public DataLoaderService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Runs once after the bean is created; imports the CSV unless data already exists.
    @PostConstruct
    public void loadData() {
        if (movieRepository.count() > 0) return; // already loaded

        // Open the bundled CSV file from the classpath.
        try (Reader reader = new InputStreamReader(
                new ClassPathResource("data/imdb_top_1000.csv").getInputStream());
             CSVReader csv = new CSVReader(reader)) {

            List<Movie> batch = new ArrayList<>();
            String[] line;
            csv.readNext(); // skip header
            // Read each row and map its columns into a Movie object.
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
                    // Only keep rows that have a title.
                    if (m.getTitle() != null && !m.getTitle().isBlank()) {
                        batch.add(m);
                    }
                } catch (Exception ignored) {} // skip any malformed row
                // Flush to the database in batches of 100 for efficiency.
                if (batch.size() >= 100) {
                    movieRepository.saveAll(batch);
                    batch.clear();
                }
            }
            // Save any remaining movies that didn't fill a full batch.
            if (!batch.isEmpty()) movieRepository.saveAll(batch);
            System.out.println(">> Dataset loaded: " + movieRepository.count() + " movies");
        } catch (Exception e) {
            System.err.println("Could not load dataset: " + e.getMessage());
        }
    }

    // Safely read a column from the row, returning "" if it's missing.
    private String safe(String[] arr, int idx) {
        return idx < arr.length && arr[idx] != null ? arr[idx].trim() : "";
    }
    // Parse an integer year, stripping non-digits; defaults to 2000 on failure.
    private Integer parseInt(String s) {
        try { return Integer.parseInt(s.replaceAll("[^0-9]", "")); } catch (Exception e) { return 2000; }
    }
    // Parse a rating into a float; defaults to 0.0 on failure.
    private @NotNull(message = "Rating is required") @DecimalMin(value = "0.0", message = "Rating must be at least 0") @DecimalMax(value = "10.0", message = "Rating  must be at least 0") Float parseDouble(String s) {
        try { return (float) Double.parseDouble(s); } catch (Exception e) { return 0.0F; }
    }
    // Cap the overview text at 1000 characters to fit the column limit.
    private String truncate(String s) {
        return s == null ? "" : (s.length() > 1000 ? s.substring(0, 1000) : s);
    }
}
