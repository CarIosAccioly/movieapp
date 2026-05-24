package com.movie_catalog.movieapp.service;

import com.movie_catalog.movieapp.dto.UserRegistrationDto;
import com.movie_catalog.movieapp.model.User;
import com.movie_catalog.movieapp.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

// Handles user registration and supplies Spring Security with user details for authentication.
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection of the repository and password encoder.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Build a new USER-role account from the form data, hashing the password before saving.
    public User register(UserRegistrationDto userRegistrationDto) {
        User user = User.builder()
                .username(userRegistrationDto.getUsername())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .email(userRegistrationDto.getEmail())
                .fullName(userRegistrationDto.getFullName())
                .role("USER")
                .build();

        return userRepository.save(user);
    }

    // True if the username is already in use.
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // True if the email is already registered.
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Called by Spring Security at login: loads the user and maps their role to an authority.
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
