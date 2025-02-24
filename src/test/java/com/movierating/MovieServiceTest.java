package com.movierating;

import com.movierating.exception.ResourceNotFoundException;
import com.movierating.model.Movie;
import com.movierating.model.Rating;
import com.movierating.repository.MovieRepository;
import com.movierating.repository.RatingRepository;
import com.movierating.service.MovieService;
import com.movierating.strategy.AverageRatingStrategy;
import com.movierating.strategy.RatingContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository = Mockito.mock(MovieRepository.class);
        ratingRepository = Mockito.mock(RatingRepository.class);
        movieService = new MovieService(movieRepository, ratingRepository);
    }

    @Test
    void testAddMovie() {
        String title = "Test Movie";
        Movie movie = new Movie.Builder().title(title).build();
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie savedMovie = movieService.addMovie(title);
        ArgumentCaptor<Movie> movieCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieRepository).save(movieCaptor.capture());
        assertEquals(title, movieCaptor.getValue().getTitle());
        assertEquals(title, savedMovie.getTitle());
    }

    @Test
    void testGetAllMoviesEmpty() {
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        movieService.getAllMovies();
        verify(movieRepository).findAll();
    }

    @Test
    void testAddRatingMovieNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        // Assert that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> movieService.addRating(1L, 4));
        verify(movieRepository).findById(1L);
        verifyNoInteractions(ratingRepository);
    }

    @Test
    void testAddRatingSuccess() {
        Movie movie = new Movie.Builder().title("Test Movie").build();
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        movieService.addRating(1L, 5);
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void testCalculateAggregatedRatingNoRatings() {
        Movie movie = new Movie.Builder().title("Test Movie").build();
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(ratingRepository.findByMovie(movie)).thenReturn(Collections.emptyList());
        // Assert that ResourceNotFoundException is thrown when there are no ratings
        assertThrows(ResourceNotFoundException.class, () -> movieService.calculateAggregatedRating(1L));
        verify(movieRepository).findById(1L);
        verify(ratingRepository).findByMovie(movie);
    }

    @Test
    void testCalculateAggregatedRatingWithRatings() {
        Movie movie = new Movie.Builder().title("Test Movie").build();
        Rating rating1 = new Rating();
        rating1.setRating(4);
        Rating rating2 = new Rating();
        rating2.setRating(5);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(ratingRepository.findByMovie(movie)).thenReturn(Arrays.asList(rating1, rating2));
        // Set strategy to Average
        movieService.setRatingStrategy(new RatingContext(new AverageRatingStrategy()));
        double aggregatedRating = movieService.calculateAggregatedRating(1L);
        // Expected average: (4+5)/2 = 4.5
        assertEquals(4.5, aggregatedRating);
    }
}
