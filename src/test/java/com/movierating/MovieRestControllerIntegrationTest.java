package com.movierating;

import com.movierating.dto.MovieDTO;
import com.movierating.dto.RatingDTO;
import com.movierating.main.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class MovieRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAndGetMovie() {
        // Create a new movie via REST API
        MovieDTO newMovie = new MovieDTO();
        newMovie.setTitle("Integration Test Movie");

        ResponseEntity<MovieDTO> createResponse = restTemplate.postForEntity("/api/movies", newMovie, MovieDTO.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        MovieDTO createdMovie = createResponse.getBody();
        assertNotNull(createdMovie);
        assertNotNull(createdMovie.getId());
        assertEquals("Integration Test Movie", createdMovie.getTitle());

        // Retrieve the created movie by id
        ResponseEntity<MovieDTO> getResponse = restTemplate.getForEntity("/api/movies/" + createdMovie.getId(), MovieDTO.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        MovieDTO fetchedMovie = getResponse.getBody();
        assertNotNull(fetchedMovie);
        assertEquals(createdMovie.getId(), fetchedMovie.getId());
        assertEquals(createdMovie.getTitle(), fetchedMovie.getTitle());
    }

    @Test
    public void testAddRatingAndCalculateRating() {
        // Create a new movie
        MovieDTO newMovie = new MovieDTO();
        newMovie.setTitle("Rating Test Movie");

        ResponseEntity<MovieDTO> createResponse = restTemplate.postForEntity("/api/movies", newMovie, MovieDTO.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        MovieDTO createdMovie = createResponse.getBody();
        assertNotNull(createdMovie);
        Long movieId = createdMovie.getId();

        // Add a rating to the movie
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRating(4);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RatingDTO> ratingEntity = new HttpEntity<>(ratingDTO, headers);
        ResponseEntity<Void> ratingResponse = restTemplate.postForEntity("/api/movies/" + movieId + "/ratings", ratingEntity, Void.class);
        assertEquals(HttpStatus.OK, ratingResponse.getStatusCode());

        // Calculate aggregated rating (should be 4.0 for a single rating of 4)
        ResponseEntity<Double> aggregatedResponse = restTemplate.getForEntity("/api/movies/" + movieId + "/rating", Double.class);
        assertEquals(HttpStatus.OK, aggregatedResponse.getStatusCode());
        Double aggregatedRating = aggregatedResponse.getBody();
        assertNotNull(aggregatedRating);
        assertEquals(4.0, aggregatedRating);
    }
}
