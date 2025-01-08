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

/**
 * Handles the customer-specific menu and its operations, such as viewing games,
 * managing the game library, handling the shopping cart, and managing wallet funds.
 */
public class CustomerMenu {
    private final CustomerController customerController;
    private final GameController gameController;
    private final ShoppingCartController shoppingCartController;
    private final ReviewController reviewController;
    private final MainMenu mainMenu;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs the CustomerMenu with the required controllers and the main menu.
     *
     * @param customerController       The controller for customer-specific operations.
     * @param gameController           The controller for game operations.
     * @param mainMenu                 The main menu reference.
     * @param loggedInCustomer         The currently logged-in customer.
     * @param shoppingCartController   The controller for shopping cart operations.
     * @param reviewController         The controller for review operations.
     */
    public CustomerMenu(CustomerController customerController, GameController gameController, MainMenu mainMenu, Customer loggedInCustomer, ShoppingCartController shoppingCartController, ReviewController reviewController) {
        this.customerController = customerController;
        this.gameController = gameController;
        this.mainMenu = mainMenu;
        this.shoppingCartController = shoppingCartController;
        this.reviewController = reviewController;
        this.customerController.setLoggedInCustomer(loggedInCustomer);
        this.reviewController.setLoggedInCustomer(loggedInCustomer);
    }

    //MENUS

    /**
     * Displays the customer menu and handles user input for different actions.
     */
    public void start() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View All Games");
            System.out.println("2. Search Game by Name");
            System.out.println("3. Sort/Filter Games by");
            System.out.println("4. Add Funds to your Wallet");
            System.out.println("5. View Wallet Balance");
            System.out.println("6. Game Library Options");
            System.out.println("7. Make a Purchase");
            System.out.println("8. View my Own Orders");
            System.out.println("9. Delete Account");
            System.out.println("10. Log Out");
            System.out.println("11. Exit\n");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> mainMenu.handleViewAllGames();
                case 2 -> handleSearchGameByName();
                case 3 -> handleSortFilterGames();
                case 4 -> handleAddFundsToWallet();
                case 5 -> handleViewWalletBalance();
                case 6 -> handleGameLibraryOptionsMenu();
                case 7 -> handleShoppingCartMenu();
                case 8 -> handleViewCustomerOrderHistory();
                case 9 -> {
                    mainMenu.handleDeleteAccount();
                    return;
                }
                case 10 -> {
                    mainMenu.handleLogOut();
                    return;
                }
                case 11 -> mainMenu.exitApp();
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Handles the sorting and filtering menu for games, allowing the user to sort by name or price,
     * or filter by genre or price range.
     */
    private void handleSortFilterGames() {
        while (true) {
            System.out.println("\nSort/Filter Games Menu:");
            System.out.println("1. Sort Games by Name (Ascending)");
            System.out.println("2. Sort Games by Price (Descending)");
            System.out.println("3. Filter Games by Genre");
            System.out.println("4. Filter Games by Price Range");
            System.out.println("5. Return to Customer Menu\n");
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

    /**
     * Displays and handles the game library options menu, allowing the customer to add reviews,
     * view reviews for a specific game, view all reviews, or return to the main customer menu.
     */
    private void handleGameLibraryOptionsMenu() {
        handleViewGamesLibrary();

        while (true) {
            System.out.println("\nGame Library Options:");
            System.out.println("1. Add Review to a Game");
            System.out.println("2. View Reviews for a Game");
            System.out.println("3. View All Reviews");
            System.out.println("4. Return to Customer Menu\n");
            System.out.print("Select option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 ->{
                    handleLeaveReview();
                    handleViewGamesLibrary();
                }
                case 2 -> {
                    handleViewReviewsForGame();
                    handleViewGamesLibrary();
                }
                case 3 -> handleViewAllReviews();
                case 4 -> {return;}
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays and handles the shopping cart menu, allowing the customer to list all games,
     * view the cart, add or remove games, view the total price, checkout, or return to the customer menu.
     */
    private void handleShoppingCartMenu() {
        while (true) {
            System.out.println("\nShopping Cart Menu:");
            System.out.println("1. List All Games");
            System.out.println("2. View Cart");
            System.out.println("3. Add Game to Cart");
            System.out.println("4. Remove Game from Cart");
            System.out.println("5. View Cart Total Price");
            System.out.println("6. Checkout");
            System.out.println("7. Return to Customer Menu\n");
            System.out.print("Select option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> handleListAllGames();
                case 2 -> handleViewCart();
                case 3 -> handleAddGameToCart();
                case 4 -> handleRemoveGameFromCart();
                case 5 -> handleViewCartTotalPrice();
                case 6 -> {
                    handleCheckout();
                    return;
                }
                case 7 -> {
                    System.out.println("Returning to Customer Menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    //1

    /**
     * Lists all available games in the shopping cart repository.
     * Displays the games' names and prices.
     */
    private void handleListAllGames() {
        try {
            List<Game> allGames = shoppingCartController.getAllGames();
            if (allGames.isEmpty()) {
                throw new EntityNotFoundException("No games available.");
            } else {
                System.out.println("Available Games:");
                for (Game game : allGames) {
                    System.out.println("- " + game.getGameName() + " ($" + game.getPrice() + ")");
                }
            }
        } catch (EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //2
    /**
     * Searches for games by name or partial name provided by the user.
     * Displays the details of matching games.
     */
    private void handleSearchGameByName() {
        System.out.print("Enter the game name or a part of it: ");
        String gameName = scanner.nextLine().trim();

        try {
            List<Game> matchingGames = customerController.searchGameByName(gameName);
            if (matchingGames.isEmpty()) {
                throw new EntityNotFoundException("No games found with the name: " + gameName);
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
        } catch (EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while searching for games: " + e.getMessage());
        }
    }

    //3.1
    /**
     * Handles sorting games by name in ascending order.
     * Retrieves the sorted list of games and displays them.
     * Throws an error if no games are available to sort.
     */
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
        } catch (Exception ex) {
            System.out.println("An unexpected error occurred: " + ex.getMessage());
        }
    }

    //3.2
    /**
     * Handles sorting games by price in descending order.
     * Retrieves the sorted list of games and displays them.
     * Throws an error if no games are available to sort.
     */
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
        } catch (Exception ex) {
            System.out.println("An unexpected error occurred: " + ex.getMessage());
        }
    }

    //3.3
    /**
     * Handles filtering games by genre.
     * Prompts the user for a genre, retrieves matching games, and displays them.
     * Throws an error if the genre is empty or no games match the genre.
     */
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
        } catch (Exception ex) {
            System.out.println("An unexpected error occurred: " + ex.getMessage());
        }
    }

    //3.4
    /**
     * Handles filtering games by price range.
     * Prompts the user for a minimum and maximum price, retrieves matching games, and displays them.
     * Throws an error if the price range is invalid or no games match the range.
     */
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

    //4
    /**
     * Allows the customer to add funds to their wallet.
     * Prompts the user for a payment method and the amount to add.
     * Displays an error message if the amount is invalid or the operation fails.
     */
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

    //5
    /**
     * Displays the customer's current wallet balance.
     * Displays an error message if no customer is logged in.
     */
    private void handleViewWalletBalance() {
        try {
            float balance = customerController.getWalletBalance();
            System.out.println("Your current wallet balance is: $" + balance);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //6
    /**
     * Displays the customer's game library.
     * If the library is empty, it shows a message indicating no games are available.
     * Displays details of each game, including reviews if available.
     */
    private void handleViewGamesLibrary() {
        try {
            List<Game> gamesLibrary = customerController.viewGamesLibrary();
            if (gamesLibrary.isEmpty()) {
                throw new EntityNotFoundException("Your Game Library is empty.");
            } else {
                System.out.println("Your Game Library:");
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
        } catch (EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //6.1

    /**
     * Allows the customer to leave a review for a game in their library.
     * Prompts the user for the game ID and a rating (1-5).
     * Displays a success message upon successful submission or an error message for invalid inputs.
     */
    private void handleLeaveReview() {
        try {
            System.out.print("Enter the ID of the game to review: ");
            int gameId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter your rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();

            reviewController.leaveReview(gameId, rating);
            System.out.println("Review successfully added!\n");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    //6.2
    /**
     * Displays all reviews for a specific game based on its ID.
     * Prompts the user for the game ID and displays each review, including the rating and reviewer.
     * If no reviews are found, a message is displayed.
     */
    private void handleViewReviewsForGame() {
        try {
            System.out.print("Enter the ID of the game to view reviews: ");
            int gameId = scanner.nextInt();
            scanner.nextLine();

            List<Review> reviews = reviewController.getReviewsForGame(gameId);
            if (reviews.isEmpty()) {
                throw new EntityNotFoundException("No reviews found for this game.");
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

    //6.3
    /**
     * Displays all reviews in the system.
     * If no reviews are found, a message is displayed.
     * Displays each review, including the game name, rating, and reviewer.
     */
    private void handleViewAllReviews() {
        try {
            List<Review> allReviews = reviewController.getAllReviews();
            if (allReviews.isEmpty()) {
                throw new EntityNotFoundException("No reviews found.");
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

    //7.2
    /**
     * Displays the contents of the customer's shopping cart.
     * If the cart is empty, a message is displayed.
     * Otherwise, it lists all games in the cart with their names and prices.
     */
    private void handleViewCart() {
        try {
            int shoppingCartId = customerController.getShoppingCartId();
            ShoppingCart cart = shoppingCartController.getShoppingCart(shoppingCartId);
            List<Game> gamesInCart = cart.getListOfGames();

            if (gamesInCart.isEmpty()) {
                throw new EntityNotFoundException("Your cart is empty.");
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

    //7.3
    /**
     * Allows the customer to add a game to their shopping cart.
     * Prompts the user for the game ID and adds it to the cart if it's not already there.
     * Displays a success message upon completion or an error message for invalid input or logic issues.
     */
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

    //7.4
    /**
     * Removes a game from the customer's shopping cart.
     * Prompts the user for the game ID to remove and updates the cart if successful.
     * Displays appropriate messages for success or errors.
     */
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

    //7.5
    /**
     * Displays the total price of all games in the customer's shopping cart.
     * Fetches the cart details and calculates the total price.
     * Displays appropriate messages for errors or success.
     */
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

    //7.6
    /**
     * Completes the checkout process for the customer's shopping cart.
     * Fetches the cart details, processes the payment, and finalizes the purchase.
     * Displays a success message or error messages for any issues.
     */
    private void handleCheckout() {
        try {
            int shoppingCartId = customerController.getShoppingCartId();
            shoppingCartController.checkout(shoppingCartId);
            System.out.println("Checkout completed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //8
    /**
     * Displays the order history specific to the currently logged-in customer.
     * Fetches the customer's orders from the shopping cart controller and displays them.
     * Displays a message if no orders exist or in case of an error.
     */
    private void handleViewCustomerOrderHistory() {
        try {
            Customer loggedInCustomer = customerController.getLoggedInCustomer();
            if (loggedInCustomer == null) {
                throw new EntityNotFoundException("No customer is currently logged in.");
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
