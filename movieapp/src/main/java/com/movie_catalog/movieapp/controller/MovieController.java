package com.movie_catalog.movieapp.controller;

import com.movie_catalog.movieapp.model.Movie;
import com.movie_catalog.movieapp.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("movies",  movieService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "movies/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        return "movies/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("mode", "create");
        return "movies/form";
    }

    // CREATE - submit
    @PostMapping
    public String create(@Valid @ModelAttribute("movie") Movie movie,
                         BindingResult br, Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "create");
            return "movies/form";
        }
        movieService.save(movie);
        ra.addFlashAttribute("success", "Movie added successfully!");
        return "redirect:/movies";
    }

    // UPDATE - form
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("mode", "edit");
        return "movies/form";
    }

    // UPDATE - submit
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("movie") Movie movie,
                         BindingResult br, Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "movies/form";
        }
        movie.setId(id);
        movieService.save(movie);
        ra.addFlashAttribute("success", "Movie updated successfully!");
        return "redirect:/movies";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        movieService.delete(id);
        ra.addFlashAttribute("success", "Movie deleted successfully!");
        return "redirect:/movies";
    }
}
