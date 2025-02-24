package com.movierating.controller;

import com.movierating.dto.MovieDTO;
import com.movierating.dto.RatingDTO;
import com.movierating.model.Movie;
import com.movierating.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieRestController {

    private final MovieService movieService;

    @Autowired
    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    // GET /api/movies - List all movies
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movies = movieService.getAllMovies().stream()
                .map(MovieDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    // GET /api/movies/{id} - Get a movie by id
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable("id") Long id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(MovieDTO.fromEntity(movie));
    }

    // POST /api/movies - Create a new movie
    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        Movie movie = movieService.addMovie(movieDTO.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(MovieDTO.fromEntity(movie));
    }

    // POST /api/movies/{id}/ratings - Add a rating to a movie
    @PostMapping("/{id}/ratings")
    public ResponseEntity<Void> addRating(@PathVariable("id") Long id, @RequestBody RatingDTO ratingDTO) {
        movieService.addRating(id, ratingDTO.getRating());
        return ResponseEntity.ok().build();
    }

    // GET /api/movies/{id}/rating - Calculate aggregated rating for a movie
    @GetMapping("/{id}/rating")
    public ResponseEntity<Double> calculateRating(@PathVariable("id") Long id) {
        double aggregatedRating = movieService.calculateAggregatedRating(id);
        return ResponseEntity.ok(aggregatedRating);
    }
}
