package com.movie_catalog.movieapp.config;

import com.movie_catalog.movieapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Central Spring Security configuration: authentication and URL access rules.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    // Authenticates users via UserService and verifies passwords with BCrypt.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    // Defines which URLs are public vs. protected, plus the login and logout behavior.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // Home, auth pages, and static assets are open to everyone.
                        .requestMatchers("/", "/register", "/login", "/css/**", "/images/**", "/js/**").permitAll()
                        // Everything else requires being logged in.
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Use the custom login page; redirect to /movies on success.
                        .loginPage("/login")
                        .defaultSuccessUrl("/movies", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        // Send users back to the login page after logging out.
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );
        return http.build();
    }
}
