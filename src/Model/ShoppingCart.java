package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Shopping Cart for a Customer, which can also act as an Order in the database.
 */
public class ShoppingCart implements HasId {
    private int shoppingCartId;
    private List<Game> listOfGames;
    private Customer customer;
    private String status; // "ACTIVE", "CHECKED_OUT", etc.

    /**
     * Constructs a ShoppingCart with the specified details.
     *
     * @param shoppingCartId The unique identifier for the shopping cart.
     * @param customer       The customer owning the shopping cart.
     */
    public ShoppingCart(int shoppingCartId, Customer customer) {
        this.shoppingCartId = shoppingCartId;
        this.customer = customer;
        this.listOfGames = new ArrayList<>();
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public List<Game> getListOfGames() {
        return listOfGames;
    }

    public void setListOfGames(List<Game> listOfGames) {
        this.listOfGames = listOfGames;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public boolean isCheckedOut() {
//        return "CHECKED_OUT".equals(status);
//    }

    public void clear() {
        this.listOfGames.clear();
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCartId=" + shoppingCartId +
                ", listOfGames=" + listOfGames +
                ", customer=" + (customer != null ? customer.getUsername() : "unknown") +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return shoppingCartId;
    }
}
