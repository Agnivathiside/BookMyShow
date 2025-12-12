package com.bookMyShowClone.movieService.dto.responseDto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {

    private UUID id;

    private String title;
    private String description;
    private String language;
    private Integer duration;
    private String genre;

    private BigDecimal rating;
    private LocalDate releaseDate;

    private String posterUrl;
    private String bannerUrl;

    private Boolean isActive;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
