package Service;

import Model.Admin;
import Model.Discount;
import Model.Game;
import Repository.IRepository;
import Exception.BusinessLogicException;

/**
 * Service class for admin-specific functions, such as managing games and applying discounts.
 */
public class AdminService {
    private final IRepository<Game> gameRepository;
    private final IRepository<Admin> adminRepository;
    private final IRepository<Discount> discountRepository;
    private Admin loggedInAdmin;

    /**
     * Constructs the AdminService with game, admin, and discount repositories.
     *
     * @param gameRepository The repository for managing games.
     * @param adminRepository The repository for managing admins.
     * @param discountRepository The repository for managing discounts.
     */
    public AdminService(IRepository<Game> gameRepository, IRepository<Admin> adminRepository, IRepository<Discount> discountRepository) {
        this.gameRepository = gameRepository;
        this.adminRepository = adminRepository;
        this.discountRepository = discountRepository;
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
        //System.out.println("Game with ID " + gameId + " has been successfully deleted.");
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

    /**
     * Retrieves the currently logged-in admin.
     *
     * @return The logged-in admin, or null if no admin is logged in.
     */
    public Admin getLoggedInAdmin() {
        return loggedInAdmin;
    }
}
