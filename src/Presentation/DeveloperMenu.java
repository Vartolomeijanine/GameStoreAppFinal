package Presentation;

import Controller.DeveloperController;
import Controller.GameController;
import Model.Developer;
import Model.Game;
import Model.GameGenre;

import java.util.List;
import java.util.Scanner;

public class DeveloperMenu {
    private final DeveloperController developerController;
    private final GameController gameController;
    private final MainMenu mainMenu;
    private final Scanner scanner = new Scanner(System.in);

    public DeveloperMenu(DeveloperController developerController, GameController gameController, MainMenu mainMenu, Developer loggedInDeveloper) {
        this.developerController = developerController;
        this.gameController = gameController;
        this.mainMenu = mainMenu;
        this.developerController.setDeveloper(loggedInDeveloper);
    }

    public void start() {
        while (true) {
            System.out.println("\nDeveloper Menu:");
            System.out.println("1. View All Games");
            System.out.println("2. View Game by ID");
            System.out.println("3. Publish Game");
            System.out.println("4. Modify Game");
            System.out.println("5. View Published Games");
            System.out.println("6. Delete Account");
            System.out.println("7. Log Out");
            System.out.println("8. Exit");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> mainMenu.handleViewAllGames();
                case 2 -> mainMenu.handleViewGame(gameController);
                case 3 -> handlePublishGame();
                case 4 -> handleModifyGame();
                case 5 -> handleViewPublishedGames();
                case 6 -> {mainMenu.handleDeleteAccount(); return;}
                case 7 -> {mainMenu.handleLogOut(); return;}
                case 8 -> System.exit(0);
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void handlePublishGame() {
        System.out.print("Enter Game Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Game Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Game Genre (e.g., ACTION): ");
        String genre = scanner.nextLine().toUpperCase();
        System.out.print("Enter Game Price: ");
        float price = scanner.nextFloat();
        scanner.nextLine();

        Game game = new Game(null, name, description, GameGenre.valueOf(genre), price/* , null */);
        developerController.publishGame(game);
        System.out.println("Game published successfully.");
    }

    private void handleModifyGame() {
        System.out.print("Enter Game ID: ");
        int gameId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter New Genre: ");
        String genre = scanner.nextLine().toUpperCase();
        System.out.print("Enter New Price: ");
        float price = scanner.nextFloat();
        scanner.nextLine();

        developerController.modifyGame(gameId, name, description, genre, price);
        System.out.println("Game modified successfully.");
    }

    private void handleViewPublishedGames() {
        try {
            List<Game> publishedGames = developerController.getPublishedGames();
            if (publishedGames.isEmpty()) {
                System.out.println("No games have been published yet.");
            } else {
                System.out.println("Published Games:");
                for (Game game : publishedGames) {
                    System.out.println(game);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
