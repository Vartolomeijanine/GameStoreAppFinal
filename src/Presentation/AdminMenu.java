package Presentation;

import Controller.AdminController;
import Controller.GameController;
import Model.Admin;

import java.util.Scanner;

public class AdminMenu {
    private final AdminController adminController;
    private final GameController gameController;
    private final MainMenu mainMenu;
    private final Scanner scanner = new Scanner(System.in);

    public AdminMenu(AdminController adminController, GameController gameController, MainMenu mainMenu, Admin loggedInAdmin) {
        this.adminController = adminController;
        this.gameController = gameController;
        this.mainMenu = mainMenu;
        this.adminController.setLoggedInAdmin(loggedInAdmin);
    }

    public void start() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Games");
            System.out.println("2. View Game by ID");
            System.out.println("3. Delete Game");
            System.out.println("4. Apply Discount");
            System.out.println("5. Delete Account");
            System.out.println("6. Delete Any Account by Email");
            System.out.println("7. Log Out");
            System.out.println("8. Exit");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> mainMenu.handleViewAllGames();
                case 2 -> mainMenu.handleViewGame(gameController);
                case 3 -> handleDeleteGame();
                case 4 -> handleApplyDiscount();
                case 5 -> {mainMenu.handleDeleteAccount(); return;}
                case 6 -> handleDeleteAnyAccount();
                case 7 -> {mainMenu.handleLogOut(); return;}
                case 8 -> mainMenu.exitApp();
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }


    private void handleDeleteGame() {
        System.out.print("Enter Game ID to delete: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();
        adminController.deleteGame(gameId);
        System.out.println("Game with ID " + gameId + " has been successfully deleted.");

    }

    private void handleApplyDiscount() {
        System.out.print("Enter Game ID: ");
        int gameId = scanner.nextInt();
        System.out.print("Enter Discount Percentage: ");
        float discount = scanner.nextFloat();
        scanner.nextLine();
        adminController.applyDiscountToGame(gameId, discount);
        System.out.println("Discount applied successfully.");
    }

    private void handleDeleteAnyAccount() {
        System.out.print("Enter the email of the account to delete: ");
        String email = scanner.nextLine();

        try {
            adminController.deleteAnyAccount(email);
            System.out.println("Account deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }






}

