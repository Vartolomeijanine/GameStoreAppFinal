package Service;

import Model.*;
import Repository.IRepository;
import Exception.BusinessLogicException;

import javax.management.relation.Role;
import java.util.List;

/**
 * Service class for admin-specific functions, such as managing games and applying discounts.
 */
public class AdminService {
    private final IRepository<Game> gameRepository;
    private final IRepository<Admin> adminRepository;
    private final IRepository<Discount> discountRepository;
    private final IRepository<User> userRepository;
    private final IRepository<Developer> developerRepository;
    private final IRepository<Customer> customerRepository;
    private Admin loggedInAdmin;

    /**
     * Constructs the AdminService with game, admin, and discount repositories.
     *
     * @param gameRepository The repository for managing games.
     * @param adminRepository The repository for managing admins.
     * @param discountRepository The repository for managing discounts.
     */
    public AdminService(IRepository<Game> gameRepository, IRepository<Admin> adminRepository, IRepository<Discount> discountRepository, IRepository<User> userRepository, IRepository<Developer> developerRepository, IRepository<Customer> customerRepository) {
        this.gameRepository = gameRepository;
        this.adminRepository = adminRepository;
        this.discountRepository = discountRepository;
        this.userRepository = userRepository;
        this.developerRepository = developerRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Sets the currently logged-in admin.
     *
     * @param admin The admin to set as logged in.
     */
    public void setLoggedInAdmin(Admin admin) {
        this.loggedInAdmin = admin;
    }

    /**
     * Deletes a game from the repository by its ID.
     *
     * @param gameId The ID of the game to delete.
     * @throws BusinessLogicException if no admin is logged in or the game is not found.
     */
    public void deleteGame(int gameId) {
        if (loggedInAdmin == null) {
            throw new BusinessLogicException("You must be logged in as an admin to delete games.");
        }

        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new BusinessLogicException("Game with ID " + gameId + " not found.");
        }
        gameRepository.delete(gameId);
    }

    /**
     * Applies a discount to a specific game.
     *
     * @param gameId The ID of the game to apply the discount to.
     * @param discountPercentage The discount percentage to apply.
     * @throws BusinessLogicException if no admin is logged in or the game is not found.
     */
    public void applyDiscountToGame(int gameId, float discountPercentage) {
        if (loggedInAdmin == null) {
            throw new BusinessLogicException("You must be logged in as an admin to apply discounts.");
        }

        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new BusinessLogicException("Game with ID " + gameId + " not found.");
        }

        Discount discount = new Discount(gameId, discountPercentage);
        discountRepository.create(discount);
        game.setDiscount(discount);
        gameRepository.update(game);

        float discountedPrice = game.getPrice() * (1 - discountPercentage / 100);
        System.out.println("Discount of " + discountPercentage + "% applied to game: " + game.getGameName());
        System.out.println("New discounted price: " + discountedPrice);
    }

    public boolean deleteAnyAccount(String email) {
        User userToDelete = null;

        List<Admin> admins = adminRepository.getAll();
        for (Admin admin : admins) {
            if (admin.getEmail().equalsIgnoreCase(email)) {
                userToDelete = admin;
                adminRepository.delete(admin.getId());
                return true;
            }
        }

        List<Developer> developers = developerRepository.getAll();
        for (Developer developer : developers) {
            if (developer.getEmail().equalsIgnoreCase(email)) {
                userToDelete = developer;
                developerRepository.delete(developer.getId());
                return true;
            }
        }
        if (customerRepository != null) {
            List<Customer> customers = customerRepository.getAll();
            for (Customer customer : customers) {
                if (customer.getEmail().equalsIgnoreCase(email)) {
                    userToDelete = customer;
                    ShoppingCart shoppingCart = customer.getShoppingCart();
                    if (shoppingCart != null) {
                        shoppingCart.getListOfGames().clear();
                    }
                    if (customer.getReviews() != null) {
                        customer.getReviews().clear();
                    }
                    if (customer.getGamesLibrary() != null) {
                        customer.getGamesLibrary().clear();
                    }
                    customerRepository.delete(customer.getId());
                    return true;
                }
            }
        }

        List<User> users = userRepository.getAll();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                userToDelete = user;
                userRepository.delete(user.getId());
                return true;
            }
        }

        if (userToDelete == null) {
            throw new BusinessLogicException("User with the given email does not exist.");
        }

        return false;
    }

    /**
     * Retrieves the currently logged-in admin.
     *
     * @return The logged-in admin, or null if no admin is logged in.
     */
    public Admin getLoggedInAdmin() {
        return loggedInAdmin;
    }
}
