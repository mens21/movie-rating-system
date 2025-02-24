package com.movierating.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    // One-to-many relationship with Rating
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    // Private constructor for Builder Pattern
    private Movie(Builder builder) {
        this.title = builder.title;
    }

    // Default constructor needed by JPA
    public Movie() {}

    // Getters and setters
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<Rating> getRatings() {
        return ratings;
    }
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    // Builder Pattern for constructing Movie objects
    public static class Builder {
        private String title;

        public Builder() {}

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
