package com.movierating.dto;

import com.movierating.model.Movie;

/**
 * Data Transfer Object for Movie.
 * Used to decouple internal Movie entity from API responses.
 */
public class MovieDTO {
    private Long id;
    private String title;

    public MovieDTO() {}

    public MovieDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // Convert a Movie entity to a MovieDTO
    public static MovieDTO fromEntity(Movie movie) {
        return new MovieDTO(movie.getId(), movie.getTitle());
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
