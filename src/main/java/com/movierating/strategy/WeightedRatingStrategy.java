package com.movierating.strategy;

import java.util.List;

/**
 * WeightedRatingStrategy implements the Strategy Pattern.
 * It assigns higher weight to earlier ratings.
 */
public class WeightedRatingStrategy implements RatingStrategy {
    @Override
    public double calculateRating(List<Integer> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        double weightSum = 0;
        int weight = ratings.size();
        for (int rating : ratings) {
            total += rating * weight;
            weightSum += weight;
            weight--;
        }
        return total / weightSum;
    }
}
