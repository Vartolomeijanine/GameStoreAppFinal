package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements HasId ,Serializable {
    private Integer shoppingCartId;
    private List<Game> listOfGames;
    private Customer customer;

    /**
     * Constructs a shopping cart for the specified customer and game list.
     *
     * @param shoppingCartId The unique identifier for the shopping cart.
     * @param customer       The customer who owns the cart.
     * @param listOfGames    The list of games in the cart.
     */
    public ShoppingCart(Integer shoppingCartId, Customer customer, List<Game> listOfGames) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        this.shoppingCartId = shoppingCartId;
        this.customer = customer;
        this.listOfGames = listOfGames != null ? new ArrayList<>(listOfGames) : new ArrayList<>();
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public List<Game> getListOfGames() {
        return new ArrayList<>(listOfGames); // Return a copy to ensure encapsulation
    }

    public void setListOfGames(List<Game> listOfGames) {
        this.listOfGames = listOfGames != null ? new ArrayList<>(listOfGames) : new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCartId=" + shoppingCartId +
                ", listOfGames=" + listOfGames +
                ", customer=" + (customer != null ? customer.getUsername() : "unknown") +
                '}';
    }

    @Override
    public Integer getId() {
        return shoppingCartId;
    }
}
