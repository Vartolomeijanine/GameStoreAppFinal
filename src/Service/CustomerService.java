package Service;

import Model.*;
import Repository.IRepository;
import Exception.BusinessLogicException;
import Exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerService {
    private final IRepository<Game> gameRepository;
    private final IRepository<User> userRepository;
    private final IRepository<Customer> customerRepository;
    private final IRepository<Review> reviewRepository;
    private final IRepository<PaymentMethod> paymentMethodRepository;
    private Customer loggedInCustomer;

    public CustomerService(IRepository<Game> gameRepository, IRepository<User> userRepository, IRepository<Customer> customerRepository, IRepository<Review> reviewRepository, IRepository<PaymentMethod> paymentMethodRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.reviewRepository = reviewRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public void setLoggedInCustomer(Customer Customer) {
        this.loggedInCustomer = Customer;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public List<Game> searchGameByName(String name) {
        List<Game> matchingGames = new ArrayList<>();
        if (loggedInCustomer != null) {
            List<Game> allGames = gameRepository.getAll(); // Presupunem că ai un gameRepository accesibil în CustomerService
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

    public void addFundsToWallet(String paymentMethod, float amount) {
        if (loggedInCustomer == null) {
            throw new IllegalStateException("No customer is logged in.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        loggedInCustomer.setFundWallet(loggedInCustomer.getFundWallet() + amount);
        System.out.println("Funds added via " + paymentMethod + ". New balance: $" + loggedInCustomer.getFundWallet());
    }


    public float getWalletBalance() {
        if (loggedInCustomer == null) {
            throw new IllegalStateException("No customer is logged in.");
        }
        return loggedInCustomer.getFundWallet();
    }

    public List<Game> viewGamesLibrary() {
        if (loggedInCustomer == null) {
            throw new IllegalStateException("No customer is logged in.");
        }

        List<Game> gamesLibrary = loggedInCustomer.getGamesLibrary();
        if (gamesLibrary.isEmpty()) {
            throw new EntityNotFoundException("Your games library is empty.");
        }

        return gamesLibrary;
    }



}
