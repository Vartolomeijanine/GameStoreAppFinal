package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a finalized order made by a customer.
 */
public class Order implements HasId {
    private int orderId;
    private List<Game> purchasedGames;
    private Customer customer;

    /**
     * Constructs an Order with the specified details.
     *
     * @param orderId        The unique identifier for the order.
     * @param customer       The customer who placed the order.
     * @param purchasedGames The list of games included in the order.
     */
    public Order(int orderId, Customer customer, List<Game> purchasedGames) {
        this.orderId = orderId;
        this.customer = customer;
        this.purchasedGames = purchasedGames;
    }


    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Game> getPurchasedGames() {
        return purchasedGames;
    }

    public void setPurchasedGames(List<Game> purchasedGames) {
        this.purchasedGames = new ArrayList<>(purchasedGames);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", purchasedGames=" + purchasedGames +
                ", customer=" + (customer != null ? customer.getUsername() : "unknown") +
                '}';
    }

    @Override
    public Integer getId() {
        return orderId;
    }
}
