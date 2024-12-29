package Controller;

import Model.Admin;
import Service.AdminService;

/**
 * Controller for admin actions, such as deleting games and applying discounts.
 */

public class AdminController {
    private final AdminService adminService;

    /**
     * Constructs the AdminController with an AdminService instance.
     * @param adminService The AdminService used for admin operations.
     */

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Deletes a game from the system by its ID.
     * @param gameId The ID of the game to delete.
     */

    public void deleteGame(int gameId) {
        adminService.deleteGame(gameId);
    }

    /**
     * Applies a discount to a game by its ID.
     * @param gameId The ID of the game to apply the discount to.
     * @param discountPercentage The discount percentage to apply.
     */

    public void applyDiscountToGame(int gameId, float discountPercentage) {
        adminService.applyDiscountToGame(gameId, discountPercentage);
    }

    public boolean deleteAnyAccount(String email) {
        return adminService.deleteAnyAccount(email);
    }


    public void setLoggedInAdmin(Admin admin) {
        adminService.setLoggedInAdmin(admin);
    }


}

