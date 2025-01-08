package Service;

import Model.*;
import Repository.IRepository;
import Exception.BusinessLogicException;
import Exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service class for customer-specific functions, such as searching, filtering, and managing games in the library.
 */
public class CustomerService {
    private final IRepository<Game> gameRepository;
    private final IRepository<User> userRepository;
    private final IRepository<Customer> customerRepository;
    private final IRepository<Review> reviewRepository;
    private final IRepository<PaymentMethod> paymentMethodRepository;
    private Customer loggedInCustomer;

    /**
     * Constructs the CustomerService with the specified repositories.
     *
     * @param gameRepository The repository for managing games.
     * @param userRepository The repository for managing users.
     * @param customerRepository The repository for managing customers.
     * @param reviewRepository The repository for managing reviews.
     * @param paymentMethodRepository The repository for managing payment methods.
     */
    public CustomerService(IRepository<Game> gameRepository, IRepository<User> userRepository, IRepository<Customer> customerRepository, IRepository<Review> reviewRepository, IRepository<PaymentMethod> paymentMethodRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.reviewRepository = reviewRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    /**
     * Sets the currently logged-in customer.
     *
     * @param Customer The customer to set as logged in.
     */
    public void setLoggedInCustomer(Customer Customer) {
        this.loggedInCustomer = Customer;
    }

    /**
     * Retrieves the currently logged-in customer.
     *
     * @return The logged-in customer, or null if no customer is logged in.
     */
    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    /**
     * Searches for games by name.
     *
     * @param name The name (or part of the name) of the game to search for.
     * @return A list of games matching the search criteria.
     * @throws BusinessLogicException if no customer is logged in.
     */
    public List<Game> searchGameByName(String name) {
        List<Game> matchingGames = new ArrayList<>();
        if (loggedInCustomer != null) {
            List<Game> allGames = gameRepository.getAll();
            for (Game game : allGames) {
                if (game.getGameName().toLowerCase().contains(name.toLowerCase())) {
                    matchingGames.add(game);
                }
            }
        } else {
            throw new BusinessLogicException("No customer is logged in.");
        }
        return matchingGames;
    }

    /**
     * Sorts all games by name in ascending order.
     *
     * @return A sorted list of games.
     * @throws BusinessLogicException if no games are available to sort.
     */
    public List<Game> sortGamesByNameAscending() {
        List<Game> games = new ArrayList<>(gameRepository.getAll());
        if (games.isEmpty()) {
            throw new BusinessLogicException("No games available to sort.");
        }
        for (int i = 0; i < games.size() - 1; i++) {
            for (int j = 0; j < games.size() - i - 1; j++) {
                if (games.get(j).getGameName().compareToIgnoreCase(games.get(j + 1).getGameName()) > 0) {
                    Collections.swap(games, j, j + 1);
                }
            }
        }
        return games;
    }

    /**
     * Sorts all games by price in descending order.
     *
     * @return A sorted list of games.
     * @throws BusinessLogicException if no games are available to sort.
     */
    public List<Game> sortGamesByPriceDescending() {
        List<Game> allGames = new ArrayList<>(gameRepository.getAll());
        if (allGames.isEmpty()) {
            throw new BusinessLogicException("No games available to sort.");
        }
        for (int i = 0; i < allGames.size() - 1; i++) {
            for (int j = 0; j < allGames.size() - i - 1; j++) {
                if (allGames.get(j).getPrice() < allGames.get(j + 1).getPrice()) {
                    Collections.swap(allGames, j, j + 1);
                }
            }
        }
        return allGames;
    }

    /**
     * Filters games by genre.
     *
     * @param genre The genre to filter by.
     * @return A list of games matching the specified genre.
     * @throws BusinessLogicException if no games are found for the specified genre.
     */
    public List<Game> filterByGenre(String genre) {
        List<Game> gamesByGenre = new ArrayList<>();
        for (Game game : gameRepository.getAll()) {
            if (game.getGameGenre().name().equalsIgnoreCase(genre)) {
                gamesByGenre.add(game);
            }
        }
        if (gamesByGenre.isEmpty()) {
            throw new BusinessLogicException("No games found for the specified genre: " + genre);
        }
        return gamesByGenre;
    }

    /**
     * Filters games by a specified price range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return A list of games within the specified price range.
     * @throws BusinessLogicException if no games are found within the price range.
     */
    public List<Game> filterGamesByPriceRange(float minPrice, float maxPrice) {
        List<Game> gamesByPriceRange = new ArrayList<>();

        for (Game game : gameRepository.getAll()) {
            if (game.getPrice() >= minPrice && game.getPrice() <= maxPrice) {
                gamesByPriceRange.add(game);
            }
        }
        if (gamesByPriceRange.isEmpty()) {
            throw new BusinessLogicException("No games found in the price range: $" + minPrice + " - $" + maxPrice);
        }
        return gamesByPriceRange;
    }

    /**
     * Adds funds to the wallet of the logged-in customer.
     *
     * @param paymentMethod The payment method used to add funds.
     * @param amount The amount to add.
     * @throws EntityNotFoundException if no customer is logged in.
     * @throws BusinessLogicException if the amount is not greater than 0.
     * @throws BusinessLogicException if the customer repository is not available.
     */
    public void addFundsToWallet(String paymentMethod, float amount) {
        if (loggedInCustomer == null) {
            throw new EntityNotFoundException("No customer is logged in.");
        }
        if (amount <= 0) {
            throw new BusinessLogicException("Amount must be greater than 0.");
        }

        loggedInCustomer.setFundWallet(loggedInCustomer.getFundWallet() + amount);
        if (customerRepository != null) {
            customerRepository.update(loggedInCustomer);
        } else {
            throw new BusinessLogicException("Customer repository is not available.");
        }
        System.out.println("Funds added via " + paymentMethod + ". New balance: $" + loggedInCustomer.getFundWallet());
    }

    /**
     * Retrieves the wallet balance of the logged-in customer.
     *
     * @return The wallet balance.
     * @throws EntityNotFoundException if no customer is logged in.
     */
    public float getWalletBalance() {
        if (loggedInCustomer == null) {
            throw new EntityNotFoundException("No customer is logged in.");
        }
        return loggedInCustomer.getFundWallet();
    }

    /**
     * Views the games library of the logged-in customer.
     *
     * @return A list of games in the customer's library.
     * @throws EntityNotFoundException if no customer is logged in.
     * @throws EntityNotFoundException if the library is empty.
     */
    public List<Game> viewGamesLibrary() {
        if (loggedInCustomer == null) {
            throw new EntityNotFoundException("No customer is logged in.");
        }

        List<Game> gamesLibrary = customerRepository.get(loggedInCustomer.getId()).getGamesLibrary();
        if (gamesLibrary.isEmpty()) {
            throw new EntityNotFoundException("Your games library is empty.");
        }

        return gamesLibrary;
    }

    /**
     * Retrieves the shopping cart ID of the logged-in customer.
     *
     * @return The shopping cart ID.
     * @throws EntityNotFoundException if no customer is logged in or the shopping cart is null.
     */
    public int getShoppingCartId() {
        Customer loggedInCustomer = getLoggedInCustomer();
        if (loggedInCustomer == null) {
            throw new EntityNotFoundException("No customer is logged in.");
        }
        if (loggedInCustomer.getShoppingCart() == null) {
            throw new EntityNotFoundException("Logged-in customer does not have a shopping cart.");
        }
        return loggedInCustomer.getShoppingCart().getShoppingCartId();
    }
}
