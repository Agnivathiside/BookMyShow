package com.bookMyShowClone.movieService.repository;

import com.bookMyShowClone.movieService.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

    Page<Movie> findByIsActiveTrue(Pageable pageable);

    Optional<Movie> findByIdAndIsActiveTrue(UUID id);

    Page<Movie> findByLanguageIgnoreCaseAndIsActiveTrue(String language, Pageable pageable);

    Page<Movie> findByGenreIgnoreCaseAndIsActiveTrue(String genre, Pageable pageable);

    Page<Movie> findByLanguageIgnoreCaseAndGenreIgnoreCaseAndIsActiveTrue(
            String language,
            String genre,
            Pageable pageable
    );
}
