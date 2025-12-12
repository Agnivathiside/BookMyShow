package com.bookMyShowClone.movieService.controller;

import com.bookMyShowClone.movieService.dto.requestDto.MovieRequest;
import com.bookMyShowClone.movieService.dto.responseDto.MovieListResponse;
import com.bookMyShowClone.movieService.dto.responseDto.MovieResponse;
import com.bookMyShowClone.movieService.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public MovieResponse createMovie(@Valid @RequestBody MovieRequest request) {
        return movieService.createMovie(request);
    }

    @PutMapping("/{id}")
    public MovieResponse updateMovie(
            @PathVariable UUID id,
            @Valid @RequestBody MovieRequest request
    ) {
        return movieService.updateMovie(id, request);
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable UUID id) {
        return movieService.getMovieById(id);
    }

    @GetMapping
    public MovieListResponse listMovies(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return movieService.listMovies(language, genre, page, size);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable UUID id) {
        movieService.deleteMovie(id);
    }
}
