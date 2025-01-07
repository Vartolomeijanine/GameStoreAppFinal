package Presentation;

import Controller.CustomerController;
import Controller.GameController;
import Controller.ReviewController;
import Controller.ShoppingCartController;
import Model.Customer;
import Model.Game;
import Model.Review;
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
    private final ReviewController reviewController;
    private final MainMenu mainMenu;
    private final Scanner scanner = new Scanner(System.in);

    public CustomerMenu(CustomerController customerController, GameController gameController, MainMenu mainMenu, Customer loggedInCustomer, ShoppingCartController shoppingCartController, ReviewController reviewController) {
        this.customerController = customerController;
        this.gameController = gameController;
        this.mainMenu = mainMenu;
        this.shoppingCartController = shoppingCartController;
        this.reviewController = reviewController;
        this.customerController.setLoggedInCustomer(loggedInCustomer);
        this.reviewController.setLoggedInCustomer(loggedInCustomer);
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
            System.out.println("9. View Reviews for a Game");
            System.out.println("10. View All Reviews");
            System.out.println("11. Delete Account");
            System.out.println("12. View Order History");
            System.out.println("13. View my Own Orders");
            System.out.println("14. Log Out");
            System.out.println("15. Exit");
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
                case 8 -> handleLeaveReview();
                case 9 -> handleViewReviewsForGame();
                case 10 -> handleViewAllReviews();
                case 11 -> {
                    mainMenu.handleDeleteAccount();
                    return;
                }
                case 12 -> handleViewOrderHistory();
                case 13 -> handleViewCustomerOrderHistory();
                case 14 -> {
                    mainMenu.handleLogOut();
                    return;
                }
                case 15 -> mainMenu.exitApp();
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
                    StringBuilder gameDetails = new StringBuilder();
                    gameDetails.append("Game ID: ").append(game.getGameId()).append(", ")
                            .append("Name: ").append(game.getGameName()).append(", ")
                            .append("Description: ").append(game.getGameDescription()).append(", ")
                            .append("Genre: ").append(game.getGameGenre()).append(", ")
                            .append("Price: $").append(game.getDiscountedPrice()).append(", ");

                    if (!game.getReviews().isEmpty()) {
                        gameDetails.append("Reviews: ");
                        for (Review review : game.getReviews()) {
                            gameDetails.append(review.getRating()).append("/5 by ")
                                    .append(review.getCustomer().getUsername()).append("; ");
                        }
                    } else {
                        gameDetails.append("Reviews: No reviews");
                    }

                    System.out.println(gameDetails.toString());
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
        scanner.nextLine();

        try {
            int shoppingCartId = customerController.getShoppingCartId();
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
        scanner.nextLine();

        try {
            int shoppingCartId = customerController.getShoppingCartId();
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
            int shoppingCartId = customerController.getShoppingCartId();
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
            if (gamesLibrary.isEmpty()) {
                System.out.println("Your games library is empty.");
            } else {
                System.out.println("Your Games Library:");
                for (Game game : gamesLibrary) {
                    StringBuilder gameDetails = new StringBuilder();
                    gameDetails.append("Game ID: ").append(game.getGameId()).append(", ")
                            .append("Name: ").append(game.getGameName()).append(", ")
                            .append("Description: ").append(game.getGameDescription()).append(", ")
                            .append("Genre: ").append(game.getGameGenre()).append(", ")
                            .append("Price: $").append(game.getDiscountedPrice()).append(", ");

                    if (!game.getReviews().isEmpty()) {
                        gameDetails.append("Reviews: ");
                        for (Review review : game.getReviews()) {
                            gameDetails.append(review.getRating()).append("/5 by ")
                                    .append(review.getCustomer().getUsername()).append("; ");
                        }
                    } else {
                        gameDetails.append("Reviews: No reviews");
                    }
                    System.out.println(gameDetails.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching your games library: " + e.getMessage());
        }


    }

    private void handleViewOrderHistory() {
        try {
            List<Order> orders = shoppingCartController.getOrderHistory();
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

    private void handleLeaveReview() {
        try {
            System.out.print("Enter the ID of the game to review: ");
            int gameId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter your rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();

            reviewController.leaveReview(gameId, rating);
            System.out.println("Review successfully added!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleViewReviewsForGame() {
        try {
            System.out.print("Enter the ID of the game to view reviews: ");
            int gameId = scanner.nextInt();
            scanner.nextLine();

            List<Review> reviews = reviewController.getReviewsForGame(gameId);
            if (reviews.isEmpty()) {
                System.out.println("No reviews found for this game.");
            } else {
                System.out.println("Reviews for the game:");
                for (Review review : reviews) {
                    System.out.println("- " + review.getRating() + "/5 by " + review.getCustomer().getUsername());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleViewAllReviews() {
        try {
            List<Review> allReviews = reviewController.getAllReviews();
            if (allReviews.isEmpty()) {
                System.out.println("No reviews found.");
            } else {
                System.out.println("All Reviews:");
                for (Review review : allReviews) {
                    System.out.println("- " + review.getRating() + "/5 for " + review.getGame().getGameName() + " by " + review.getCustomer().getUsername());
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleViewCustomerOrderHistory() {
        try {
            Customer loggedInCustomer = customerController.getLoggedInCustomer();
            if (loggedInCustomer == null) {
                throw new IllegalStateException("No customer is currently logged in.");
            }

            List<Order> customerOrders = shoppingCartController.getAllOrdersByCustomer(loggedInCustomer);
            if (customerOrders.isEmpty()) {
                System.out.println("You have not placed any orders yet.");
                return;
            }

            System.out.println("Order History for " + loggedInCustomer.getUsername() + ":");
            for (Order order : customerOrders) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Games:");
                for (Game game : order.getPurchasedGames()) {
                    System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the customer's order history: " + e.getMessage());
        }
    }

}
