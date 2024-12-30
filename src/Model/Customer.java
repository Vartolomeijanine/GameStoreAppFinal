package Model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private float fundWallet;
    private List<Game> gamesLibrary;
    private List<Review> reviews;
    private ShoppingCart shoppingCart;

    /**
     * Constructs a Customer with the specified details.
     *
     * @param userId       The unique identifier for the customer.
     * @param username     The username of the customer.
     * @param email        The email of the customer.
     * @param password     The password of the customer.
     * @param role         The role of the customer ("Customer").
     * @param fundWallet   The initial funds in the customer's wallet.
     * @param gamesLibrary The list of games owned by the customer.
     * @param reviews      The list of reviews written by the customer.
     * @param shoppingCart The customer's shopping cart.
     */


    public Customer( Integer userId, String username, String email, String password, String role, float fundWallet, List<Game> gamesLibrary, List<Review> reviews, ShoppingCart shoppingCart) {
        super(userId, username, email, password, role);
        this.fundWallet = fundWallet;
        this.gamesLibrary = new ArrayList<>(gamesLibrary);
        this.reviews = new ArrayList<>(reviews);
        this.shoppingCart = shoppingCart;
    }

    public float getFundWallet() {
        return fundWallet;
    }

    public void setFundWallet(float fundWallet) {
        this.fundWallet = fundWallet;
    }

    public List<Game> getGamesLibrary() {
        return gamesLibrary;
    }

    public void setGamesLibrary(List<Game> gamesLibrary) {
        this.gamesLibrary = new ArrayList<>(gamesLibrary);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> review) {
        this.reviews = new ArrayList<>(reviews);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", fundWallet=" + fundWallet +
                ", gamesLibrary=" + gamesLibrary +
                ", reviews=" + reviews +
                ", shoppingCart=" + shoppingCart +
                '}';
    }

    @Override
    public Integer getId() {
        return userId;
    }
}
