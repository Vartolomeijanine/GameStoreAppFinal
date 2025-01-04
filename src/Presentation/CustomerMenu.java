package Presentation;

import Controller.CustomerController;
import Controller.GameController;
import Model.Customer;
import Model.Game;
import Exception.EntityNotFoundException;
import Exception.ValidationException;

import java.util.Scanner;
import java.util.List;

public class CustomerMenu {
    private final CustomerController customerController;
    private final GameController gameController;
    private final MainMenu mainMenu;
    private final Scanner scanner = new Scanner(System.in);

    public CustomerMenu(CustomerController customerController, GameController gameController, MainMenu mainMenu, Customer loggedInCustomer) {
        this.customerController = customerController;
        this.gameController = gameController;
        this.mainMenu = mainMenu;
        this.customerController.setLoggedInCustomer(loggedInCustomer);
    }

    public void start() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View All Games");
            System.out.println("2. Search Game by Name");
            System.out.println("3. Sort/Filter Games by");
            System.out.println("4. Add Funds to your Wallet");
            System.out.println("5. View Wallet Balance");
            System.out.println("6. View Games Library");
            System.out.println("7. Make a Purchase");
            System.out.println("8. Add Review to a game");
            System.out.println("9. Delete Account");
            System.out.println("10. Log Out");
            System.out.println("11. Exit");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> mainMenu.handleViewAllGames();
                case 2 -> handleSearchGameByName();
                case 3 -> handleSortFilterGames();
                case 4 -> handleAddFundsToWallet();
                case 5 -> handleViewWalletBalance();
                case 6 -> handleViewGamesLibrary();
                case 7 -> handleShoppingCartMenu();
                case 9 -> {mainMenu.handleDeleteAccount(); return;}
                case 10 -> {mainMenu.handleLogOut(); return;}
                case 11 -> mainMenu.exitApp();
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void handleSearchGameByName() {
        System.out.print("Enter the game name or a part of it: ");
        String gameName = scanner.nextLine().trim();

        try {
            List<Game> matchingGames = customerController.searchGameByName(gameName);
            if (matchingGames.isEmpty()) {
                System.out.println("No games found with the name: " + gameName);
            } else {
                System.out.println("Games matching \"" + gameName + "\":");
                for (Game game : matchingGames) {
                    System.out.println(game);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while searching for games: " + e.getMessage());
        }
    }

    private void handleSortFilterGames() {
        while (true) {
            System.out.println("\nSort/Filter Games Menu:");
            System.out.println("1. Sort Games by Name (Ascending)");
            System.out.println("2. Sort Games by Price (Descending)");
            System.out.println("3. Filter Games by Genre");
            System.out.println("4. Filter Games by Price Range");
            System.out.println("5. Return to Customer Menu");
            System.out.print("Select option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleSortGamesByNameAscending();
                case 2 -> handleSortGamesByPriceDescending();
                case 3 -> handleFilterByGenre();
                case 4 -> handleFilterGamesByPriceRange();
                case 5 -> {
                    System.out.println("Returning to Customer Menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void handleShoppingCartMenu() {
        while (true) {
            System.out.println("\nShopping Cart Menu:");
            System.out.println("1. List All Games");
            System.out.println("2. View Cart");
            System.out.println("3. Add Game to Cart");
            System.out.println("4. Remove Game from Cart");
            System.out.println("5. View Cart Total Price");
            System.out.println("6. Checkout");
            System.out.println("7. Return to Customer Menu");
            System.out.print("Select option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleListAllGames();
                case 2 -> handleViewCart();
                case 3 -> handleAddGameToCart();
                case 4 -> handleRemoveGameFromCart();
                case 5 -> handleViewCartTotalPrice();
                case 6 -> handleCheckout();
                case 7 -> {
                    System.out.println("Returning to Customer Menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void handleCheckout() {
        
    }

    private void handleViewCartTotalPrice() {
    }

    private void handleRemoveGameFromCart() {
    }

    private void handleAddGameToCart() {
    }

    private void handleViewCart() {
    }

    private void handleListAllGames() {
    }


    private void handleSortGamesByNameAscending() {
        try {
            List<Game> sortedGames = customerController.sortGamesByNameAscending();

            if (sortedGames.isEmpty()) {
                throw new EntityNotFoundException("No games available to sort.");
            } else {
                System.out.println("Games sorted by name (ascending):");
                for (Game game : sortedGames) {
                    System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
                }
            }
        } catch (EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void handleSortGamesByPriceDescending() {
        try {
            List<Game> sortedGames = customerController.sortGamesByPriceDescending();

            if (sortedGames.isEmpty()) {
                throw new EntityNotFoundException("No games available to sort.");
            } else {
                System.out.println("Games sorted by price (descending):");
                for (Game game : sortedGames) {
                    System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
                }
            }
        } catch (EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void handleFilterByGenre() {
        try {
            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();
            if (genre.isBlank()) {
                throw new ValidationException("Genre cannot be empty.");
            }

            List<Game> games = customerController.filterGamesByGenre(genre);
            if (games.isEmpty()) {
                throw new EntityNotFoundException("No games found for the specified genre.");
            } else {
                System.out.println("Games in genre " + genre + ":");
                for (Game game : games) {
                    System.out.println(game);
                }
            }
        } catch (ValidationException | EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void handleFilterGamesByPriceRange() {
        try {
            System.out.print("Enter minimum price: ");
            float minPrice = scanner.nextFloat();
            System.out.print("Enter maximum price: ");
            float maxPrice = scanner.nextFloat();
            scanner.nextLine();

            if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
                throw new ValidationException("Invalid price range provided. Minimum price must be less than or equal to maximum price and non-negative.");
            }

            List<Game> filteredGames = customerController.filterGamesByPriceRange(minPrice, maxPrice);

            if (filteredGames.isEmpty()) {
                throw new EntityNotFoundException("No games found in the specified price range.");
            } else {
                System.out.println("Games in the price range $" + minPrice + " - $" + maxPrice + ":");
                for (Game game : filteredGames) {
                    System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
                }
            }
        } catch (ValidationException | EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void handleAddFundsToWallet() {
        System.out.print("Enter payment method (e.g., Visa, AppleCard): ");
        String paymentMethod = scanner.nextLine();

        System.out.print("Enter amount to add: ");
        float amount = scanner.nextFloat();
        scanner.nextLine();

        try {
            customerController.addFundsToWallet(paymentMethod, amount);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void handleViewWalletBalance() {
        try {
            float balance = customerController.getWalletBalance();
            System.out.println("Your current wallet balance is: $" + balance);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleViewGamesLibrary() {
        try {
            List<Game> gamesLibrary = customerController.viewGamesLibrary();
            System.out.println("Your Games Library:");
            for (Game game : gamesLibrary) {
                System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while fetching your games library: " + e.getMessage());
        }
    }


}
