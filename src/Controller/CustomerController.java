package Controller;

import Model.Customer;
import Model.Game;
import Model.ShoppingCart;
import Service.CustomerService;

import java.util.List;

public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setLoggedInCustomer(Customer customer) {
        customerService.setLoggedInCustomer(customer);
    }

    public List<Game> searchGameByName(String name) {
        return customerService.searchGameByName(name);
    }

    public List<Game> sortGamesByNameAscending() {
        return customerService.sortGamesByNameAscending();
    }

    public List<Game> sortGamesByPriceDescending() {
        return customerService.sortGamesByPriceDescending();
    }

    public List<Game> filterGamesByGenre(String genre) {
        return customerService.filterByGenre(genre);
    }

    public List<Game> filterGamesByPriceRange(float minPrice, float maxPrice) {
        return customerService.filterGamesByPriceRange(minPrice, maxPrice);
    }

    public void addFundsToWallet(String paymentMethod, float amount) {
        customerService.addFundsToWallet(paymentMethod, amount);
    }


    public float getWalletBalance() {
        return customerService.getWalletBalance();
    }

    public List<Game> viewGamesLibrary() {
        return customerService.viewGamesLibrary();
    }

    public int getShoppingCartId() {
        return customerService.getShoppingCartId();
    }

    public ShoppingCart getShoppingCart() {
        return customerService.getLoggedInCustomer().getShoppingCart();
    }



}
