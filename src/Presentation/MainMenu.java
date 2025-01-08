package Presentation;

import Controller.*;
import Model.*;
import Exception.*;

import java.util.List;
import java.util.Scanner;

/**
 * The `MainMenu` class represents the main interface of the application,
 * allowing users to sign up, log in, and navigate to their respective menus
 * based on their roles (Admin, Developer, or Customer).
 */
public class MainMenu {
    private final AccountController accountController;
    private final GameController gameController;
    private final AdminController adminController;
    private final DeveloperController developerController;
    private final CustomerController customerController;
    private final ShoppingCartController shoppingCartController;
    private final ReviewController reviewController;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs the `MainMenu` with the necessary controllers.
     *
     * @param accountController        Controller for account-related operations.
     * @param gameController           Controller for game-related operations.
     * @param adminController          Controller for admin-specific operations.
     * @param developerController      Controller for developer-specific operations.
     * @param customerController       Controller for customer-specific operations.
     * @param shoppingCartController   Controller for shopping cart-related operations.
     * @param reviewController         Controller for review-related operations.
     */
    public MainMenu(AccountController accountController, GameController gameController,
                    AdminController adminController, DeveloperController developerController, CustomerController customerController, ShoppingCartController shoppingCartController, ReviewController reviewController) {
        this.accountController = accountController;
        this.gameController = gameController;
        this.adminController = adminController;
        this.developerController = developerController;
        this.customerController = customerController;
        this.shoppingCartController = shoppingCartController;
        this.reviewController = reviewController;
    }

    /**
     * Starts the main menu loop, allowing users to sign up, log in, or exit the application.
     */
    public void start() {
        while (true) {
            try {
                System.out.println("\nMain Menu:");
                System.out.println("1. Sign Up");
                System.out.println("2. Log In");
                System.out.println("3. Exit\n");
                System.out.print("Select option: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> handleSignUp();
                    case 2 -> handleLogIn();
                    case 3 -> exitApp();
                    default -> throw new ValidationException("Invalid option. Please select 1, 2, or 3.");
                }
            } catch (ValidationException e) {
                System.out.println("Input Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * Handles user sign-up by collecting input and calling the AccountController.
     * @throws ValidationException if input is invalid.
     */
    private void handleSignUp() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (accountController.signUp(username, email, password)) {
                System.out.println("Sign-up successful!");
            } else {
                throw new ValidationException("Sign-up failed! Email may already be in use.");
            }
        } catch (ValidationException e) {
            System.out.println("Input Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Handles user login by validating credentials and redirecting to the appropriate menu.
     * @throws ValidationException if email or password is invalid.
     */
    private void handleLogIn() {
        try {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (accountController.logIn(email, password)) {
                System.out.println("Login successful!");
                User loggedInUser = accountController.getLoggedInUser();
                if ("Admin".equals(loggedInUser.getRole())) {
                    Admin loggedInAdmin = (Admin) loggedInUser;
                    AdminMenu adminMenu = new AdminMenu(adminController, gameController, this, loggedInAdmin);
                    adminMenu.start();
                } else if ("Developer".equals(loggedInUser.getRole())) {
                    Developer loggedInDeveloper = (Developer) loggedInUser;
                    DeveloperMenu developerMenu = new DeveloperMenu(developerController, gameController, this, loggedInDeveloper);
                    developerMenu.start();
                } else if ("Customer".equals(loggedInUser.getRole())) {
                    Customer loggedInCustomer = (Customer) loggedInUser;
                    CustomerMenu customerMenu = new CustomerMenu(customerController, gameController, this, loggedInCustomer, shoppingCartController, reviewController);
                    customerMenu.start();
                } else {
                    throw new BusinessLogicException("Unknown role. Returning to Main Menu.");
                }
            } else {
                throw new ValidationException("Invalid email or password. Please try again.");
            }
        } catch (ValidationException e) {
            System.out.println("Login Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Handles the user logout process.
     * Attempts to log out the currently logged-in user using the AccountController.
     * If successful, a success message is displayed.
     * If no user is logged in, a BusinessLogicException message is shown.
     *
     * @throws BusinessLogicException if no user is currently logged in.
     */
    public void handleLogOut() {
        try {
            boolean success = accountController.logOut();
            if (success) {
                System.out.println("You have been logged out successfully.");
            } else {
                throw new BusinessLogicException("No user is currently logged in.");
            }
        } catch (BusinessLogicException e) {
            System.out.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while logging out: " + e.getMessage());
        }
    }

    /**
     * Displays all available games in the system.
     * If no games are available, an EntityNotFoundException is thrown.
     * For each game, details such as ID, name, description, genre, price, and reviews are displayed.
     *
     * @throws EntityNotFoundException if no games are found in the system.
     * @throws Exception for any unexpected errors during the process.
     */
    public void handleViewAllGames() {
        try {
            List<Game> allGames = gameController.getAllGames();
            if (allGames.isEmpty()) {
                throw new EntityNotFoundException("No games available.");
            } else {
                System.out.println("Available Games:");
                for (Game game : allGames) {
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
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Handles viewing the details of a specific game based on the provided Game ID.
     * Prompts the user to enter a Game ID and retrieves the game details if the ID is valid.
     * If the game is not found, an appropriate message is displayed.
     * If the input is invalid, an error message is shown.
     */
    public void handleViewGame(GameController gameController) {
        try {
            System.out.print("Enter Game ID: ");
            int gameId = scanner.nextInt();
            scanner.nextLine();

            Game game = gameController.getGameById(gameId);
            if (game != null) {
                System.out.println("Game Details: ");
                System.out.println(game);
            } else {
                System.out.println("Game with ID " + gameId + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please enter a valid Game ID.");
        }
    }

    /**
     * Handles the deletion of the currently logged-in user's account.
     * Prompts the user for confirmation before proceeding with account deletion.
     * If the account is successfully deleted, a success message is displayed, and the user is returned to the main menu.
     * If the deletion fails, an error message is shown.
     * If the user cancels the operation, a cancellation message is displayed.
     */
    public void handleDeleteAccount() {
        try {
            System.out.print("Are you sure you want to delete your account? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("yes")) {
                boolean success = accountController.deleteAccount();
                if (success) {
                    System.out.println("Your account has been successfully deleted.");
                    System.out.println("Returning to Main Menu...");
                } else {
                    throw new BusinessLogicException("Failed to delete your account. Please try again.");
                }
            } else {
                System.out.println("Account deletion canceled.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void exitApp() {
        System.out.println("Exiting the application...");
        System.exit(0);
    }
}

