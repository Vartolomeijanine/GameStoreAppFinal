package Controller;

import Model.Customer;
import Model.Review;
import Service.ReviewService;

import java.util.List;

public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Permite unui utilizator să lase un review pentru un joc cumpărat.
     *
     * @param gameId ID-ul jocului.
     * @param rating Rating-ul (1-5) oferit de utilizator.
     */
    public void leaveReview(int gameId, int rating) {
        reviewService.leaveReview(gameId, rating);
    }

    /**
     * Obține toate review-urile pentru un joc specific.
     *
     * @param gameId ID-ul jocului.
     * @return Lista de review-uri pentru jocul specificat.
     */
    public List<Review> getReviewsForGame(int gameId) {
        return reviewService.getReviewsForGame(gameId);
    }

    /**
     * Obține toate review-urile din sistem.
     *
     * @return Lista de review-uri.
     */
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    /**
     * Șterge un review specific.
     *
     * @param reviewId ID-ul review-ului care trebuie șters.
     */
    public void deleteReview(int reviewId) {
        reviewService.deleteReview(reviewId);
    }

    public void setLoggedInCustomer(Customer customer) {
        reviewService.setLoggedInCustomer(customer);
    }

}
