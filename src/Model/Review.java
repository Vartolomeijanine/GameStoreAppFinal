package Model;

import java.io.Serializable;

public class Review implements HasId, Serializable {
    private int reviewID;
    private int rating;
    private Customer customer;
    private Game game;

    /**
     * Constructs a review with the specified details.
     *
     * @param reviewID The unique identifier for the review.
     * @param rating   The numeric rating (1 to 5).
     * @param customer The customer who wrote the review.
     * @param game     The game being reviewed.
     */
    public Review(int reviewID, int rating, Customer customer, Game game) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.reviewID = reviewID;
        this.rating = rating;
        this.customer = customer;
        this.game = game;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    public String getRatingAsString() {
        return rating + "/5";
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", rating='" + getRatingAsString() + '\'' +
                ", customer='" + (customer != null ? customer.getUsername() : "unknown") + '\'' +
                ", game='" + (game != null ? game.getGameName() : "unknown") + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return reviewID;
    }
}
