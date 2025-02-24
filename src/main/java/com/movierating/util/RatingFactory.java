package com.movierating.util;

import com.movierating.model.Movie;
import com.movierating.model.Rating;

/**
 * RatingFactory implements the Factory Pattern.
 * It encapsulates the creation logic for Rating objects.
 */
public class RatingFactory {
    public static Rating createRating(int ratingValue, Movie movie) {
        Rating rating = new Rating();
        rating.setRating(ratingValue);
        rating.setMovie(movie);
        return rating;
    }
}
