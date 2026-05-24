package com.movie_catalog.movieapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// Carries registration form data and validates it before a User entity is created.
@Data
public class UserRegistrationDto {

    // Desired username; required, 3-20 characters.
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be 3-30 characters")
    private String username;

    // Display name; required.
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 50)
    private String fullName;

    // Email address; required and must be a valid format.
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    // Chosen password; required, at least 6 characters.
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Repeated password; checked against the above in the controller.
    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;
}
