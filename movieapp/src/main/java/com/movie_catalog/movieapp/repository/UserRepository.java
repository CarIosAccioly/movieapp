package com.movie_catalog.movieapp.repository;


import com.movie_catalog.movieapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Data access for User entities; Spring derives these queries automatically from the method names.
public interface UserRepository extends JpaRepository<User, Long> {

    // Look up a user by username (used during login).
    Optional<User> findByUsername(String username);
    // Check whether a username is already taken.
    boolean existsByUsername(String username);
    // Check whether an email is already registered.
    boolean existsByEmail(String email);
}
