package Service;

import Model.Customer;
import Model.Game;
import Model.Review;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private final IRepository<Review> reviewRepository;
    private final IRepository<Customer> customerRepository;
    private Customer loggedInCustomer;

    private final IRepository<Game> gameRepository;

    public ReviewService(IRepository<Review> reviewRepository, IRepository<Customer> customerRepository, IRepository<Game> gameRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Allows a customer to leave a review for a game they own.
     * @param gameId     The ID of the game being reviewed.
     * @param rating     The rating given by the customer (1-5).
     */
    public void leaveReview(int gameId, int rating) {
        if (loggedInCustomer == null) {
            throw new IllegalStateException("No customer is logged in.");
        }

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        Customer customer = loggedInCustomer;

        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }

        if (!customer.getGamesLibrary().contains(game)) {
            throw new IllegalArgumentException("Customer does not own this game.");
        }

        for (Review review : reviewRepository.getAll()) {
            if (review.getCustomer().getId() == customer.getId() && review.getGame().getGameId() == gameId) {
                throw new IllegalArgumentException("Customer has already reviewed this game.");
            }
        }

        int reviewId = generateReviewId();
        Review newReview = new Review(reviewId, rating, customer, game);
        reviewRepository.create(newReview);

        List<Review> gameReviews = game.getReviews();
        gameReviews.add(newReview);
        game.setReviews(gameReviews);
    }


    /**
     * Retrieves all reviews for a specific game.
     *
     * @param gameId The ID of the game.
     * @return A list of reviews for the game.
     */
    public List<Review> getReviewsForGame(int gameId) {
        List<Review> reviews = new ArrayList<>();
        for (Review review : reviewRepository.getAll()) {
            if (review.getGame().getGameId() == gameId) {
                reviews.add(review);
            }
        }
        return reviews;
    }

    /**
     * Retrieves all reviews made by a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of reviews made by the customer.
     */
    public List<Review> getReviewsByCustomer(int customerId) {
        List<Review> reviews = new ArrayList<>();
        for (Review review : reviewRepository.getAll()) {
            if (review.getCustomer().getId() == customerId) {
                reviews.add(review);
            }
        }
        return reviews;
    }

    /**
     * Generates a unique ID for a new review.
     *
     * @return A unique review ID.
     */
    private int generateReviewId() {
        return reviewRepository.getAll().stream()
                .mapToInt(Review::getId)
                .max()
                .orElse(0) + 1;
    }

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.getAll(); // Returnează toate review-urile din repository
    }

    public void deleteReview(int reviewId) {
        Review reviewToDelete = reviewRepository.get(reviewId);
        if (reviewToDelete == null) {
            throw new IllegalArgumentException("Review with ID " + reviewId + " not found.");
        }
        reviewRepository.delete(reviewId);
    }


}

