package com.movie_catalog.movieapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA entity mapped to the "users" table; Lombok generates getters/setters, constructors, and a builder.
@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    // Primary key, auto-incremented by the database.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Login name; must be unique and not null.
    @Column(unique = true, nullable = false)
    private String username;

    // BCrypt-hashed password (never stored in plain text).
    @Column(nullable = false)
    private String password;

    // User's email; must be unique and not null.
    @Column(unique = true, nullable = false)
    private String email;

    // Display name of the user.
    private String fullName;

    // Authorization role, e.g. "USER" or "ADMIN".
    private String role;
}
