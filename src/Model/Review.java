package Model;

import java.io.Serializable;

public class Review implements HasId, Serializable {
    private int reviewID;
    private String text;
    private Customer customer;
    private Game game;

    /**
     * Constructs a review with the specified details.
     *
     * @param reviewID The unique identifier for the review.
     * @param text     The text content of the review.
     * @param customer The customer who wrote the review.
     * @param game     The game being reviewed.
     */

    public Review(int reviewID, String text, Customer customer, Game game) {
        this.reviewID = reviewID;
        this.text = text;
        this.customer = customer;
        this.game = game;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
                ", text='" + text + '\'' +
                ", customer='" + (customer != null ? customer.getUsername() : "unknown") + '\'' +
                ", game='" + (game != null ? game.getGameName() : "unknown") + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return reviewID;
    }
}
