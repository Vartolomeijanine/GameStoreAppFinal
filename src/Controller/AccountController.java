package Controller;

import Service.AccountService;
import Model.User;

/**
 * Controller for managing user accounts, including signup, login, logout, and account deletion.
 */

public class AccountController {
    private final AccountService accountService;

    /**
     * Constructs the AccountController with an AccountService instance.
     * @param accountService The AccountService used for account operations.
     */

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Registers a new user with the provided credentials.
     * @param username The username of the new user.
     * @param email The email of the new user.
     * @param password The password of the new user.
     * @return true if registration is successful, false otherwise.
     */

    public boolean signUp(String username, String email, String password) {
        return accountService.signUp(username, email, password);
    }

    /**
     * Authenticates a user with the provided email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return true if login is successful, false otherwise.
     */

    public boolean logIn(String email, String password) {
        return accountService.logIn(email, password);
    }

    /**
     * Logs out the currently logged-in user.
     */

    public boolean logOut() {return accountService.logOut();}

    /**
     * Retrieves the currently logged-in user.
     * @return The logged-in user, or null if no user is logged in.
     */

    public User getLoggedInUser() {
        return accountService.getLoggedInUser();
    }

    /**
     * Deletes the account of the currently logged-in user.
     * @return true if account deletion is successful, false otherwise.
     */

    public boolean deleteAccount() {
        return accountService.deleteAccount();
    }

    /**
     * Deletes an account by email (admin-only function).
     * @param email The email of the account to delete.
     * @return true if the account is deleted successfully, false otherwise.
     */

    public boolean deleteAnyAccount(String email) {
        return accountService.deleteAnyAccount(email);
    }


}

