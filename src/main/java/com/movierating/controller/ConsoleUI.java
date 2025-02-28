package com.movierating.controller;

import com.movierating.model.Movie;
import com.movierating.service.MovieService;
import com.movierating.strategy.AverageRatingStrategy;
import com.movierating.strategy.MedianRatingStrategy;
import com.movierating.strategy.RatingContext;
import com.movierating.strategy.WeightedRatingStrategy;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI {
    private final MovieService movieService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(MovieService movieService) {
        this.movieService = movieService;
    }

    public void start() {
        while (true) {
            try {
                System.out.println("\n=== Movie Rating System ===");
                System.out.println("1. Add a movie");
                System.out.println("2. View all movies");
                System.out.println("3. Rate a movie");
                System.out.println("4. Set Rating Strategy");
                System.out.println("5. Calculate Movie Rating");
                System.out.println("6. Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter movie title: ");
                        String title = scanner.nextLine();
                        movieService.addMovie(title);
                        System.out.println("Movie '" + title + "' added successfully.");
                        break;
                    case 2:
                        List<Movie> movies = movieService.getAllMovies();
                        if (movies.isEmpty()) {
                            System.out.println("No movies available.");
                        } else {
                            System.out.println("Movie List:");
                            for (Movie movie : movies) {
                                System.out.println("ID: " + movie.getId() + " | Title: " + movie.getTitle());
                            }
                        }
                        break;
                    case 3:
                        rateMovie();
                        break;
                    case 4:
                        setRatingStrategy();
                        break;
                    case 5:
                        calculateMovieRating();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        System.exit(0); // Force the application to shut down
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter the correct type of data.");
                scanner.nextLine(); // clear the invalid input
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void calculateMovieRating() {
        List<Movie> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies available. Please add a movie first.");
            return;
        }
        System.out.println("Available Movies:");
        for (Movie movie : movies) {
            System.out.println("ID: " + movie.getId() + " | Title: " + movie.getTitle());
        }
        System.out.print("Enter movie ID to calculate rating: ");
        Long movieIdCalc = scanner.nextLong();
        scanner.nextLine(); // consume newline
        double aggregatedRating = movieService.calculateAggregatedRating(movieIdCalc);
        System.out.println("Aggregated rating for movie ID " + movieIdCalc + " is: " + aggregatedRating);
    }

    private void rateMovie() {
        try {
            List<Movie> movies = movieService.getAllMovies();
            if (movies.isEmpty()) {
                System.out.println("No movies available. Please add a movie first.");
                return;
            }
            System.out.println("Available Movies:");
            for (Movie movie : movies) {
                System.out.println("ID: " + movie.getId() + " | Title: " + movie.getTitle());
            }
            System.out.print("Enter the ID of the movie you want to rate: ");
            Long movieId = scanner.nextLong();
            System.out.print("Enter rating (1-5): ");
            int ratingValue = scanner.nextInt();
            scanner.nextLine(); // consume newline
            movieService.addRating(movieId, ratingValue);
            System.out.println("Rating " + ratingValue + " added for movie ID " + movieId);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numeric values for movie ID and rating.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error while rating movie: " + e.getMessage());
        }
    }

    private void setRatingStrategy() {
        try {
            System.out.println("Select Rating Strategy:");
            System.out.println("1. Average Rating");
            System.out.println("2. Weighted Rating");
            System.out.println("3. Median Rating");
            System.out.print("Choose option: ");

            int strategyChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (strategyChoice) {
                case 1:
                    movieService.setRatingStrategy(new RatingContext(new AverageRatingStrategy()));
                    System.out.println("Strategy set to Average Rating");
                    break;
                case 2:
                    movieService.setRatingStrategy(new RatingContext(new WeightedRatingStrategy()));
                    System.out.println("Strategy set to Weighted Rating");
                    break;
                case 3:
                    movieService.setRatingStrategy(new RatingContext(new MedianRatingStrategy()));
                    System.out.println("Strategy set to Median Rating");
                    break;
                default:
                    System.out.println("Invalid choice for rating strategy.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number for the strategy option.");
            scanner.nextLine();
        }
    }
}
