package com.movierating.dto;

/**
 * Data Transfer Object for Rating.
 * Used to transfer rating data between the API and the service layer.
 */
public class RatingDTO {
    private Long id;
    private int rating;

    public RatingDTO() {}

    public RatingDTO(Long id, int rating) {
        this.id = id;
        this.rating = rating;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}
