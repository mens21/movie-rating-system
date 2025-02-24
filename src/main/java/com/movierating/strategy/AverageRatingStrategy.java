package com.movierating.strategy;

import java.util.List;

/**
 * AverageRatingStrategy implements the Strategy Pattern.
 * It calculates the average of all ratings.
 */
public class AverageRatingStrategy implements RatingStrategy {
    @Override
    public double calculateRating(List<Integer> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Integer r : ratings) {
            sum += r;
        }
        return sum / ratings.size();
    }
}
