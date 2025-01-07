package Presentation;

import Controller.CustomerController;
import Controller.GameController;
import Controller.ShoppingCartController;
import Model.Customer;
import Model.Game;
import Exception.EntityNotFoundException;
import Exception.ValidationException;
import Model.Order;
import Model.ShoppingCart;

import java.util.Scanner;
import java.util.List;

public class CustomerMenu {
    private final CustomerController customerController;
    private final GameController gameController;
    private final ShoppingCartController shoppingCartController;
    private final MainMenu mainMenu;
    private final Scanner scanner = new Scanner(System.in);

    public CustomerMenu(CustomerController customerController, GameController gameController, MainMenu mainMenu, Customer loggedInCustomer, ShoppingCartController shoppingCartController) {
        this.customerController = customerController;
        this.gameController = gameController;
        this.mainMenu = mainMenu;
        this.shoppingCartController = shoppingCartController;
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
            System.out.println("11. View Order History");
            System.out.println("12. Exit");
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
                case 11 -> handleViewOrderHistory();
                case 12 -> mainMenu.exitApp();
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
        try {
            int shoppingCartId = customerController.getShoppingCartId();
            shoppingCartController.checkout(shoppingCartId);
            System.out.println("Checkout completed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleViewCartTotalPrice() {
        try {
            int shoppingCartId = customerController.getShoppingCartId();

            float totalPrice = shoppingCartController.getCartTotalPrice(shoppingCartId);

            System.out.println("Total price of the games in your cart: $" + totalPrice);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleRemoveGameFromCart() {
        System.out.print("Enter the ID of the game to remove from your cart: ");
        int gameId = scanner.nextInt();
        scanner.nextLine(); // Consumăm newline-ul rămas

        try {
            int shoppingCartId = customerController.getShoppingCartId(); // Obține ID-ul coșului de cumpărături
            shoppingCartController.removeGameFromCart(shoppingCartId, gameId);
            System.out.println("Game successfully removed from your cart!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleAddGameToCart() {
        System.out.print("Enter the ID of the game to add to your cart: ");
        int gameId = scanner.nextInt();
        scanner.nextLine(); // Consumăm newline-ul rămas

        try {
            int shoppingCartId = customerController.getShoppingCartId(); // Obține ID-ul coșului de cumpărături
            shoppingCartController.addGameToCart(shoppingCartId, gameId);
            System.out.println("Game successfully added to your cart!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

    }

    private void handleViewCart() {
        try {
            int shoppingCartId = customerController.getShoppingCartId(); // Obține ID-ul coșului de cumpărături
            ShoppingCart cart = shoppingCartController.getShoppingCart(shoppingCartId);
            List<Game> gamesInCart = cart.getListOfGames();

            if (gamesInCart.isEmpty()) {
                System.out.println("Your cart is empty.");
            } else {
                System.out.println("Games in your cart:");
                for (Game game : gamesInCart) {
                    System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleListAllGames() {
        try {
            List<Game> allGames = shoppingCartController.getAllGames();
            if (allGames.isEmpty()) {
                System.out.println("No games available.");
            } else {
                System.out.println("Available Games:");
                for (Game game : allGames) {
                    System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
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

    private void handleViewOrderHistory() {
        try {
            List<Order> orders = shoppingCartController.getOrderHistory(); // Obține istoricul comenzilor
            if (orders.isEmpty()) {
                System.out.println("You have not placed any orders yet.");
                return;
            }

            System.out.println("Your Order History:");
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Games:");
                for (Game game : order.getPurchasedGames()) {
                    System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching your order history: " + e.getMessage());
        }
    }



}
