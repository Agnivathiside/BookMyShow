package com.bookMyShowClone.movieService.service;

import com.bookMyShowClone.movieService.dto.requestDto.MovieRequest;
import com.bookMyShowClone.movieService.dto.responseDto.MovieListResponse;
import com.bookMyShowClone.movieService.dto.responseDto.MovieResponse;

import java.util.UUID;

public interface MovieService {

    MovieResponse createMovie(MovieRequest request);

    MovieResponse updateMovie(UUID id, MovieRequest request);

    MovieResponse getMovieById(UUID id);

    MovieListResponse listMovies(String language, String genre, int page, int size);

    void deleteMovie(UUID id); // soft delete (isActive = false)
}
