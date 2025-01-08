package Controller;

import Model.Customer;
import Model.Game;
import Model.ShoppingCart;
import Service.CustomerService;

import java.util.List;

/**
 * Controller for customer-specific actions, such as searching games,
 * managing wallet funds, and viewing the customer's game library.
 */
public class CustomerController {
    private final CustomerService customerService;

    /**
     * Constructs the CustomerController with a CustomerService instance.
     *
     * @param customerService The CustomerService used for customer operations.
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Sets the currently logged-in customer.
     *
     * @param customer The customer to set as logged in.
     */
    public void setLoggedInCustomer(Customer customer) {
        customerService.setLoggedInCustomer(customer);
    }

    /**
     * Searches for games by name.
     *
     * @param name The name or part of the name of the games to search for.
     */
    public List<Game> searchGameByName(String name) {
        return customerService.searchGameByName(name);
    }

    /**
     * Sorts all games by name in ascending order.
     *
     * @return A list of games sorted by name.
     */
    public List<Game> sortGamesByNameAscending() {
        return customerService.sortGamesByNameAscending();
    }

    /**
     * Sorts all games by price in descending order.
     *
     * @return A list of games sorted by price.
     */
    public List<Game> sortGamesByPriceDescending() {
        return customerService.sortGamesByPriceDescending();
    }

    /**
     * Filters games by genre.
     *
     * @param genre The genre to filter games by.
     */
    public List<Game> filterGamesByGenre(String genre) {
        return customerService.filterByGenre(genre);
    }


    /**
     * Filters games by a price range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     */
    public List<Game> filterGamesByPriceRange(float minPrice, float maxPrice) {
        return customerService.filterGamesByPriceRange(minPrice, maxPrice);
    }

    /**
     * Adds funds to the customer's wallet.
     *
     * @param paymentMethod The payment method used to add funds.
     * @param amount        The amount to add to the wallet.
     */
    public void addFundsToWallet(String paymentMethod, float amount) {
        customerService.addFundsToWallet(paymentMethod, amount);
    }

    /**
     * Retrieves the current balance of the customer's wallet.
     *
     * @return The current wallet balance.
     */
    public float getWalletBalance() {
        return customerService.getWalletBalance();
    }

    /**
     * Retrieves the games in the customer's library.
     *
     * @return A list of games in the customer's library.
     */
    public List<Game> viewGamesLibrary() {
        return customerService.viewGamesLibrary();
    }

    /**
     * Retrieves the ID of the customer's shopping cart.
     *
     * @return The shopping cart ID.
     */
    public int getShoppingCartId() {
        return customerService.getShoppingCartId();
    }

    /**
     * Retrieves the currently logged-in customer.
     *
     * @return The logged-in customer, or null if no customer is logged in.
     */
    public Customer getLoggedInCustomer() {
        return customerService.getLoggedInCustomer();
    }
}
