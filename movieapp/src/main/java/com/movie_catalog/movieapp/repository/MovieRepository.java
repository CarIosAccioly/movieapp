package com.movie_catalog.movieapp.repository;


import com.movie_catalog.movieapp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%' ))" +
        "OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "OR LOWER(m.director) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Movie> search(@Param("keyword") String keyword);
}
