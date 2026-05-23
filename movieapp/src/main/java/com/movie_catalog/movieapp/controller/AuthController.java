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

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto dto,
                           BindingResult br, Model model) {

        // Backend validation logic (additional to annotations)
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            br.addError(new FieldError("user", "confirmPassword", "Passwords do not match"));
        }
        if (userService.usernameExists(dto.getUsername())) {
            br.addError(new FieldError("user", "username", "Username already taken"));
        }
        if (userService.emailExists(dto.getEmail())) {
            br.addError(new FieldError("user", "email", "Email already registered"));
        }

        if (br.hasErrors()) return "register";

        userService.register(dto);
        return "redirect:/login?registered=true";
    }
}
