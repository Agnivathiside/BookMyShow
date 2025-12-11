package com.bookMyShowClone.movieService.service.impl;

import com.bookMyShowClone.movieService.dto.requestDto.MovieRequest;
import com.bookMyShowClone.movieService.dto.responseDto.MovieListResponse;
import com.bookMyShowClone.movieService.dto.responseDto.MovieResponse;
import com.bookMyShowClone.movieService.entity.Movie;
import com.bookMyShowClone.movieService.exception.MovieNotFoundException;
import com.bookMyShowClone.movieService.mapper.MovieMapper;
import com.bookMyShowClone.movieService.repository.MovieRepository;
import com.bookMyShowClone.movieService.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieResponse createMovie(MovieRequest request) {
        Movie movie = movieMapper.toEntity(request);
        Movie saved = movieRepository.save(movie);
        return movieMapper.toResponse(saved);
    }

    @Override
    public MovieResponse updateMovie(UUID id, MovieRequest request) {
        Movie movie = movieRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        movieMapper.updateEntity(movie, request);
        Movie saved = movieRepository.save(movie);

        return movieMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getMovieById(UUID id) {
        Movie movie = movieRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        return movieMapper.toResponse(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieListResponse listMovies(String language, String genre, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Movie> moviePage;

        if (language != null && genre != null) {
            moviePage = movieRepository.findByLanguageIgnoreCaseAndGenreIgnoreCaseAndIsActiveTrue(
                    language, genre, pageable
            );
        } else if (language != null) {
            moviePage = movieRepository.findByLanguageIgnoreCaseAndIsActiveTrue(
                    language, pageable
            );
        } else if (genre != null) {
            moviePage = movieRepository.findByGenreIgnoreCaseAndIsActiveTrue(
                    genre, pageable
            );
        } else {
            moviePage = movieRepository.findByIsActiveTrue(pageable);
        }

        return MovieListResponse.builder()
                .content(moviePage.map(movieMapper::toResponse).getContent())
                .page(moviePage.getNumber())
                .size(moviePage.getSize())
                .totalElements(moviePage.getTotalElements())
                .totalPages(moviePage.getTotalPages())
                .build();
    }

    @Override
    public void deleteMovie(UUID id) {
        Movie movie = movieRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        movie.setIsActive(false);
        movieRepository.save(movie);
    }
}
