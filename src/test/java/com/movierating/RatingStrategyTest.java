package com.movierating;

import com.movierating.strategy.AverageRatingStrategy;
import com.movierating.strategy.MedianRatingStrategy;
import com.movierating.strategy.WeightedRatingStrategy;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingStrategyTest {

    @Test
    public void testAverageRatingStrategy() {
        AverageRatingStrategy avgStrategy = new AverageRatingStrategy();
        // For ratings [4, 5, 3], the average should be (4+5+3)/3 = 4.0
        double result = avgStrategy.calculateRating(Arrays.asList(4, 5, 3));
        assertEquals(4.0, result);
    }

    @Test
    public void testWeightedRatingStrategy() {
        WeightedRatingStrategy weightedStrategy = new WeightedRatingStrategy();
        // For ratings [4, 5, 3]:
        // Weighted calculation: (4*3 + 5*2 + 3*1) / (3+2+1) = (12+10+3)/6 = 25/6 ≈ 4.1667
        double result = weightedStrategy.calculateRating(Arrays.asList(4, 5, 3));
        assertEquals(25.0/6, result, 0.0001);
    }

    @Test
    public void testMedianRatingStrategyOddCount() {
        MedianRatingStrategy medianStrategy = new MedianRatingStrategy();
        // For odd number of ratings [4, 5, 3], sorted -> [3, 4, 5], median is 4
        double result = medianStrategy.calculateRating(Arrays.asList(4, 5, 3));
        assertEquals(4.0, result);
    }

    @Test
    public void testMedianRatingStrategyEvenCount() {
        MedianRatingStrategy medianStrategy = new MedianRatingStrategy();
        // For even number of ratings [4, 5, 3, 6], sorted -> [3, 4, 5, 6], median is (4+5)/2 = 4.5
        double result = medianStrategy.calculateRating(Arrays.asList(4, 5, 3, 6));
        assertEquals(4.5, result);
    }
}
