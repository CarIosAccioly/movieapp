package com.movie_catalog.movieapp.controller;

import com.movie_catalog.movieapp.dto.UserRegistrationDto;
import com.movie_catalog.movieapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// Handles login and registration pages.
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // GET /login : render the login form (Spring Security processes the submission).
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // GET /register : render the empty registration form.
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    // POST /register : validate input, create the account, then redirect to login.
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto dto,
                           BindingResult br, Model model) {

        // Backend validation logic (additional to annotations)
        // Ensure the two password fields match.
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            br.addError(new FieldError("user", "confirmPassword", "Passwords do not match"));
        }
        // Reject usernames/emails that are already in use.
        if (userService.usernameExists(dto.getUsername())) {
            br.addError(new FieldError("user", "username", "Username already taken"));
        }
        if (userService.emailExists(dto.getEmail())) {
            br.addError(new FieldError("user", "email", "Email already registered"));
        }

        // If anything failed, redisplay the form with the errors.
        if (br.hasErrors()) return "register";

        userService.register(dto);
        return "redirect:/login?registered=true";
    }
}
