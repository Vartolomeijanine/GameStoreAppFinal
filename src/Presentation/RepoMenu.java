package Presentation;

import Controller.*;
import Repository.FileRepository;
import Repository.InMemoryRepository;
import Repository.IRepository;
import Service.*;
import Model.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * The `RepoMenu` class is responsible for initializing and managing different types
 * of repositories (In-Memory, File-based, or Database) and starting the application.
 */
public class RepoMenu {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the repository selection menu and initializes the application
     * based on the user's choice.
     */
    public void start() {
        System.out.println("Select Repository Type:");
        System.out.println("1. In-Memory");
        System.out.println("2. File");
        System.out.println("3. Database (Not Implemented)");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> initializeInMemory();
            case 2 -> initializeInFile();
            case 3 -> System.out.println("Database repository not implemented yet.");
            default -> {
                System.out.println("Invalid choice. Exiting...");
                System.exit(0);
            }
        }
    }

    /**
     * Populates the game repository with sample data.
     *
     * @param gameRepository The repository where sample games will be added.
     */
    private void initializeGames(IRepository<Game> gameRepository) {
        List<Game> sampleGames = List.of(
                new Game(1, "Cyber Adventure", "Explore a cyberpunk city filled with secrets.", GameGenre.ADVENTURE, 59.99f, new ArrayList<>()),
                new Game(2, "Space Warfare", "A space-themed shooter with intergalactic battles.", GameGenre.SHOOTER, 49.99f, new ArrayList<>()),
                new Game(3, "Mystic Quest", "Solve mysteries in a fantasy world.", GameGenre.RPG, 39.99f, new ArrayList<>()),
                new Game(4, "Farm Builder", "Create and manage your own virtual farm.", GameGenre.RPG, 19.99f, new ArrayList<>()),
                new Game(5, "Puzzle Challenge", "Solve various puzzles to progress through levels.", GameGenre.PUZZLE, 9.99f, new ArrayList<>())
        );

        for (Game game : sampleGames) {
            gameRepository.create(game);
        }

        System.out.println("Sample games have been added to the repository.");
    }

    /**
     * Initializes the application with in-memory repositories.
     * Creates repositories, services, and controllers, and starts the main menu.
     */
    private void initializeInMemory() {
        IRepository<Game> gameRepository = new InMemoryRepository<>();
        IRepository<User> userRepository = new InMemoryRepository<>();
        IRepository<Admin> adminRepository = new InMemoryRepository<>();
        IRepository<Developer> developerRepository = new InMemoryRepository<>();
        IRepository<Discount> discountRepository = new InMemoryRepository<>();
        IRepository<Customer> customerRepository = new InMemoryRepository<>();
        IRepository<Review> reviewRepository = new InMemoryRepository<>();
        IRepository<PaymentMethod> paymentMethodRepository = new InMemoryRepository<>();
        IRepository<ShoppingCart> shoppingCartRepository = new InMemoryRepository<>();
        IRepository<Order> orderRepository = new InMemoryRepository<>();


        AccountService accountService = new AccountService(userRepository, adminRepository, developerRepository, customerRepository, shoppingCartRepository);
        GameService gameService = new GameService(gameRepository);
        AdminService adminService = new AdminService(gameRepository, adminRepository, discountRepository, userRepository, developerRepository, customerRepository);
        DeveloperService developerService = new DeveloperService(gameRepository, developerRepository);
        CustomerService customerService = new CustomerService(gameRepository, userRepository, customerRepository, reviewRepository, paymentMethodRepository);
        ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingCartRepository, gameRepository, orderRepository, customerRepository);
        OrderService orderService = new OrderService(orderRepository);
        ReviewService reviewService = new ReviewService(reviewRepository, customerRepository, gameRepository);


        AccountController accountController = new AccountController(accountService);
        GameController gameController = new GameController(gameService);
        AdminController adminController = new AdminController(adminService);
        DeveloperController developerController = new DeveloperController(developerService);
        CustomerController customerController = new CustomerController(customerService);
        ShoppingCartController shoppingCartController = new ShoppingCartController(shoppingCartService, orderService);
        ReviewController reviewController = new ReviewController(reviewService);

        initializeGames(gameRepository);

        MainMenu mainMenu = new MainMenu(accountController, gameController, adminController, developerController, customerController, shoppingCartController, reviewController);
        mainMenu.start();
    }

    /**
     * Initializes the application with file-based repositories.
     * Creates repositories, services, and controllers, and starts the main menu.
     */
    private void initializeInFile() {
        IRepository<Game> gameRepository = new FileRepository<>("games.dat");
        IRepository<User> userRepository = new FileRepository<>("users.dat");
        IRepository<Admin> adminRepository = new FileRepository<>("admins.dat");
        IRepository<Developer> developerRepository = new FileRepository<>("developers.dat");
        IRepository<Discount> discountRepository = new FileRepository<>("discounts.dat");
        IRepository<Customer> customerRepository = new FileRepository<>("customers.dat");
        IRepository<Review> reviewRepository = new FileRepository<>("reviews.dat");
        IRepository<PaymentMethod> paymentMethodRepository = new FileRepository<>("paymentMethods.dat");
        IRepository<ShoppingCart> shoppingCartRepository = new FileRepository<>("shoppingCarts.dat");
        IRepository<Order> orderRepository = new FileRepository<>("orders.dat");


        AccountService accountService = new AccountService(userRepository, adminRepository, developerRepository, customerRepository, shoppingCartRepository);
        GameService gameService = new GameService(gameRepository);
        AdminService adminService = new AdminService(gameRepository, adminRepository, discountRepository, userRepository, developerRepository, customerRepository);
        DeveloperService developerService = new DeveloperService(gameRepository, developerRepository);
        CustomerService customerService = new CustomerService(gameRepository, userRepository, customerRepository, reviewRepository, paymentMethodRepository);
        ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingCartRepository, gameRepository, orderRepository, customerRepository);
        OrderService orderService = new OrderService(orderRepository);
        ReviewService reviewService = new ReviewService(reviewRepository, customerRepository, gameRepository);


        AccountController accountController = new AccountController(accountService);
        GameController gameController = new GameController(gameService);
        AdminController adminController = new AdminController(adminService);
        DeveloperController developerController = new DeveloperController(developerService);
        CustomerController customerController = new CustomerController(customerService);
        ShoppingCartController shoppingCartController = new ShoppingCartController(shoppingCartService, orderService);
        ReviewController reviewController = new ReviewController(reviewService);

        initializeGames(gameRepository);

        MainMenu mainMenu = new MainMenu(accountController, gameController, adminController, developerController, customerController, shoppingCartController, reviewController);
        mainMenu.start();
    }

}

