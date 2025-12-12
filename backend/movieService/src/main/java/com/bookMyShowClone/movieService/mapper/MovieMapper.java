package com.bookMyShowClone.movieService.mapper;

import com.bookMyShowClone.movieService.dto.requestDto.MovieRequest;
import com.bookMyShowClone.movieService.dto.responseDto.MovieResponse;
import com.bookMyShowClone.movieService.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie toEntity(MovieRequest request) {
        if (request == null) return null;

        return Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .language(request.getLanguage())
                .duration(request.getDuration())
                .genre(request.getGenre())
                .rating(request.getRating())
                .releaseDate(request.getReleaseDate())
                .posterUrl(request.getPosterUrl())
                .bannerUrl(request.getBannerUrl())
                .isActive(true)
                .build();
    }

    public MovieResponse toResponse(Movie movie) {
        if (movie == null) return null;

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .language(movie.getLanguage())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .posterUrl(movie.getPosterUrl())
                .bannerUrl(movie.getBannerUrl())
                .isActive(movie.getIsActive())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();
    }
    public void updateEntity(Movie movie, MovieRequest request) {
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setLanguage(request.getLanguage());
        movie.setDuration(request.getDuration());
        movie.setGenre(request.getGenre());
        movie.setRating(request.getRating());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setBannerUrl(request.getBannerUrl());
    }

}
