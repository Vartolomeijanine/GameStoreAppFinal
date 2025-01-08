package Controller;

import Model.Customer;
import Model.Review;
import Service.ReviewService;

import java.util.List;

/**
 * Controller for managing reviews, such as adding reviews for games
 * and retrieving reviews for specific games or all games.
 */
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * Constructs the ReviewController with a ReviewService instance.
     *
     * @param reviewService The ReviewService used for review operations.
     */
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Allows a user to leave a review for a purchased game.
     *
     * @param gameId The ID of the game to review.
     * @param rating The rating (1-5) given by the user.
     */
    public void leaveReview(int gameId, int rating) {
        reviewService.leaveReview(gameId, rating);
    }

    /**
     * Retrieves all reviews for a specific game.
     *
     * @param gameId The ID of the game.
     * @return A list of reviews for the specified game.
     */
    public List<Review> getReviewsForGame(int gameId) {
        return reviewService.getReviewsForGame(gameId);
    }

    /**
     * Retrieves all reviews in the system.
     *
     * @return A list of all reviews.
     */
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }


    /**
     * Sets the currently logged-in customer.
     *
     * @param customer The customer to set as logged in.
     */
    public void setLoggedInCustomer(Customer customer) {
        reviewService.setLoggedInCustomer(customer);
    }

}
