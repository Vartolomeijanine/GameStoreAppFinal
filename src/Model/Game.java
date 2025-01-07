package Model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a video game that can be purchased by users.
 * Each game has attributes such as name, genre, price, and reviews.
 */

public class Game implements HasId, Serializable {

    private Integer gameId;
    private String gameName;
    private String gameDescription;
    private GameGenre gameGenre;
    private float price;
    private Discount discount;
    List<Review> reviews;

    /**
     * Constructs a Game with the specified details.
     *
     * @param gameId          The unique identifier for the game.
     * @param gameName        The name of the game.
     * @param gameDescription The description of the game.
     * @param gameGenre       The genre of the game (e.g., ACTION, ADVENTURE).
     * @param price           The price of the game.
     * //@param reviews         A list of reviews for the game.
     */

    public Game(Integer gameId, String gameName, String gameDescription, GameGenre gameGenre, float price, List<Review> reviews) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gameGenre = gameGenre;
        this.price = price;
        this.reviews = (reviews != null) ? new ArrayList<>(reviews) : new ArrayList<>();
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public GameGenre getGameGenre() {return gameGenre;}

    public void setGameGenre(GameGenre gameGenre) {this.gameGenre = gameGenre;}

    public float getPrice() {
        return price;
    }

    /**
     * Calculates and returns the price of the game after applying any discount.
     * @return The discounted price, or the original price if no discount is applied.
     */


    public float getDiscountedPrice() {
        if (discount != null) {
            return price * (1 - discount.getDiscountPercentage() / 100);
        }
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public List<Review> getReviews() {return reviews;}

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews != null ? new ArrayList<>(reviews) : new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", gameGenre='" + gameGenre + '\'' +
                ", price=" + getDiscountedPrice() +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public Integer getId() {
        return gameId;
    }
}

