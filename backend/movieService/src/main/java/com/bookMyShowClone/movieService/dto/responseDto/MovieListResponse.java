package com.bookMyShowClone.movieService.dto.responseDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieListResponse {

    private List<MovieResponse> content;

    private int page;
    private int size;

    private long totalElements;
    private int totalPages;
}
