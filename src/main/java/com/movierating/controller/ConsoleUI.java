package com.movierating.controller;

import com.movierating.service.MovieService;
import com.movierating.strategy.*;
import org.springframework.stereotype.Component;
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
            System.out.println("\n=== Movie Rating System ===");
            System.out.println("1. Add a movie");
            System.out.println("2. View all movies");
            System.out.println("3. Rate a movie");
            System.out.println("4. Set Rating Strategy");
            System.out.println("5. Calculate Movie Rating");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            final int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter movie title: ");
                    final String title = scanner.nextLine();
                    movieService.addMovie(title);
                    break;
                case 2:
                    movieService.getAllMovies();
                    break;
                case 3:
                    System.out.print("Enter movie ID to rate: ");
                    final Long movieId = scanner.nextLong();
                    System.out.print("Enter rating (1-5): ");
                    int ratingValue = scanner.nextInt();
                    movieService.addRating(movieId, ratingValue);
                    break;
                case 4:
                    setRatingStrategy();
                    break;
                case 5:
                    System.out.print("Enter movie ID to calculate rating: ");
                    final Long movieIdCalc = scanner.nextLong();
                    movieService.calculateAggregatedRating(movieIdCalc);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void setRatingStrategy() {
        System.out.println("Select Rating Strategy:");
        System.out.println("1. Average Rating");
        System.out.println("2. Weighted Rating");
        System.out.println("3. Median Rating");
        System.out.print("Choose option: ");

        final int strategyChoice = scanner.nextInt();
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
                System.out.println("Invalid choice.");
        }
    }
}
