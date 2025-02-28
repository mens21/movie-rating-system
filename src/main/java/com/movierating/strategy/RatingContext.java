package com.movierating.strategy;

import java.util.List;

/**
 * RatingContext holds a RatingStrategy and uses it to calculate ratings.
 * This is the core of the Strategy Pattern implementation.
 */
public class RatingContext {
    private final RatingStrategy strategy;

    public RatingContext(RatingStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeStrategy(List<Integer> ratings) {
        return strategy.calculateRating(ratings);
    }
}
