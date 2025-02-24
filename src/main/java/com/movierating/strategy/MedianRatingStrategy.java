package com.movierating.strategy;

import java.util.Collections;
import java.util.List;

/**
 * MedianRatingStrategy implements the Strategy Pattern.
 * It calculates the median of the ratings.
 */
public class MedianRatingStrategy implements RatingStrategy {
    @Override
    public double calculateRating(List<Integer> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        Collections.sort(ratings);
        int n = ratings.size();
        if (n % 2 == 1) {
            return ratings.get(n / 2);
        } else {
            return (ratings.get(n / 2 - 1) + ratings.get(n / 2)) / 2.0;
        }
    }
}
