package com.movierating.service;

import com.movierating.exception.ResourceNotFoundException;
import com.movierating.model.Movie;
import com.movierating.model.Rating;
import com.movierating.repository.MovieRepository;
import com.movierating.repository.RatingRepository;
import com.movierating.strategy.AverageRatingStrategy;
import com.movierating.strategy.RatingContext;
import com.movierating.util.RatingFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private RatingContext ratingContext;

    public MovieService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        // Default to Average Rating Strategy
        this.ratingContext = new RatingContext(new AverageRatingStrategy());
    }

    public Movie addMovie(String title) {
        Movie movie = new Movie.Builder().title(title).build();
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    public void addRating(Long movieId, int ratingValue) {
        Movie movie = getMovieById(movieId);
        Rating rating = RatingFactory.createRating(ratingValue, movie);
        ratingRepository.save(rating);
    }

    public double calculateAggregatedRating(Long movieId) {
        Movie movie = getMovieById(movieId);
        List<Rating> ratings = ratingRepository.findByMovie(movie);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings available for movie with id: " + movieId);
        }
        List<Integer> ratingValues = ratings.stream()
                .map(Rating::getRating)
                .collect(Collectors.toList());
        return ratingContext.executeStrategy(ratingValues);
    }

    public void setRatingStrategy(RatingContext context) {
        this.ratingContext = context;
    }

    public RatingContext getRatingContext() {
        return ratingContext;
    }
}
