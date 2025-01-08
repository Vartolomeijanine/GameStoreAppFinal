package Service;

import Model.Customer;
import Model.Game;
import Model.Review;
import Repository.IRepository;
import Exception.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing reviews, including adding, retrieving, and deleting reviews.
 */

public class ReviewService {
    private final IRepository<Review> reviewRepository;
    private final IRepository<Customer> customerRepository;
    private Customer loggedInCustomer;

    private final IRepository<Game> gameRepository;

    /**
     * Constructs the ReviewService with the specified repositories.
     *
     * @param reviewRepository   The repository for managing reviews.
     * @param customerRepository The repository for managing customers.
     * @param gameRepository     The repository for managing games.
     */
    public ReviewService(IRepository<Review> reviewRepository, IRepository<Customer> customerRepository, IRepository<Game> gameRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Allows the logged-in customer to leave a review for a game they own.
     *
     * @param gameId The ID of the game being reviewed.
     * @param rating The rating given by the customer (1-5).
     * @throws EntityNotFoundException if no customer is logged in, the game is not found,
     *                                  or the customer does not own the game.
     * @throws ValidationException if the provided rating is not between 1 and 5.
     * @throws BusinessLogicException if the customer has already reviewed the game.
     */
    public void leaveReview(int gameId, int rating) {
        if (loggedInCustomer == null) {
            throw new EntityNotFoundException("No customer is logged in.");
        }

        if (rating < 1 || rating > 5) {
            throw new ValidationException("Rating must be between 1 and 5.");
        }

        Customer customer = customerRepository.get(loggedInCustomer.getId());

        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new EntityNotFoundException("Game not found.");
        }

        var customerGameLibrary = customer.getGamesLibrary();

        if (!customerGameLibrary.contains(game)) {
            throw new EntityNotFoundException("Customer does not own this game.");
        }

        for (Review review : reviewRepository.getAll()) {
            if (review.getCustomer().getId() == customer.getId() && review.getGame().getGameId() == gameId) {
                throw new BusinessLogicException("Customer has already reviewed this game.");
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

    /**
     * Sets the currently logged-in customer.
     *
     * @param customer The customer to set as logged in.
     */
    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }

    /**
     * Retrieves all reviews from the repository.
     *
     * @return A list of all reviews.
     */
    public List<Review> getAllReviews() {
        return reviewRepository.getAll();
    }

}

