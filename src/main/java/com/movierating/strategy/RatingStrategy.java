package com.movierating.strategy;

import java.util.List;

public interface RatingStrategy {
    double calculateRating(List<Integer> ratings);
}
