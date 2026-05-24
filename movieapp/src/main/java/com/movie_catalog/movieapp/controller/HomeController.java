package com.movie_catalog.movieapp.controller;

import com.movie_catalog.movieapp.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Serves the public landing page.
@Controller
public class HomeController {

    private final MovieService movieService;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    // GET / : show the home page with the total movie count.
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movieCount", movieService.count());
        return "home";
    }
}
