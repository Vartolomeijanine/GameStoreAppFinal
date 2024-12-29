package Presentation;

import Controller.*;
import Repository.InMemoryRepository;
import Repository.IRepository;
import Service.*;
import Model.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class RepoMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Select Repository Type:");
        System.out.println("1. In-Memory");
        System.out.println("2. File (Not Implemented)");
        System.out.println("3. Database (Not Implemented)");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> initializeInMemory();
            case 2 -> System.out.println("File repository not implemented yet.");
            case 3 -> System.out.println("Database repository not implemented yet.");
            default -> {
                System.out.println("Invalid choice. Exiting...");
                System.exit(0);
            }
        }
    }

    private void initializeGames(IRepository<Game> gameRepository) {
        List<Game> sampleGames = List.of(
                new Game(1, "Cyber Adventure", "Explore a cyberpunk city filled with secrets.", GameGenre.ADVENTURE, 59.99f/*, new ArrayList<>() */),
                new Game(2, "Space Warfare", "A space-themed shooter with intergalactic battles.", GameGenre.SHOOTER, 49.99f/*, new ArrayList<>() */),
                new Game(3, "Mystic Quest", "Solve mysteries in a fantasy world.", GameGenre.RPG, 39.99f/*, new ArrayList<>() */),
                new Game(4, "Farm Builder", "Create and manage your own virtual farm.", GameGenre.RPG, 19.99f/*, new ArrayList<>() */),
                new Game(5, "Puzzle Challenge", "Solve various puzzles to progress through levels.", GameGenre.PUZZLE, 9.99f/*, new ArrayList<>() */)
        );

        for (Game game : sampleGames) {
            gameRepository.create(game);
        }

        System.out.println("Sample games have been added to the repository.");
    }

    private void initializeInMemory() {
        // Initialize repositories
        IRepository<Game> gameRepository = new InMemoryRepository<>();
        IRepository<User> userRepository = new InMemoryRepository<>();
        IRepository<Admin> adminRepository = new InMemoryRepository<>();
        System.out.println("Initializing Admin Repository...");
        IRepository<Developer> developerRepository = new InMemoryRepository<>();
        IRepository<Discount> discountRepository = new InMemoryRepository<>();

        // Initialize services
        // Initialize services pentru InMemory
        AccountService accountService = new AccountService(userRepository);
        GameService gameService = new GameService(gameRepository);
        AdminService adminService = new AdminService(gameRepository, adminRepository, discountRepository);
        DeveloperService developerService = new DeveloperService(gameRepository, developerRepository);

        // Initialize controllers
        AccountController accountController = new AccountController(accountService);
        GameController gameController = new GameController(gameService);
        AdminController adminController = new AdminController(adminService);
        DeveloperController developerController = new DeveloperController(developerService);

        initializeGames(gameRepository);

        // Start main menu
        MainMenu mainMenu = new MainMenu(accountController, gameController, adminController, developerController);
        mainMenu.start();
    }

}

