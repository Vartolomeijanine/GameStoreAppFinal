package Service;

import Model.*;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import Exception.BusinessLogicException;
import Repository.InMemoryRepository;

/**
 * Service class for managing user accounts, including authentication, role-based signup, and account deletion.
 */

public class AccountService {
    private final IRepository<User> userRepository; //ambele
    private final IRepository<Admin> adminRepository;
    private final  IRepository<Developer> developerRepository;
    private final IRepository<Customer> customerRepository;
    private User loggedInUser;

    /**
     * Constructs the AccountService with a user repository.
     * @param userRepository The repository for storing and retrieving users.
     */

    public AccountService(IRepository<User> userRepository, IRepository<Admin> adminRepository, IRepository<Developer> developerRepository, IRepository<Customer> customerRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository != null ? adminRepository : new InMemoryRepository<>();
        this.developerRepository = developerRepository != null ? developerRepository : new InMemoryRepository<>();
        this.customerRepository = customerRepository;
    }

    /**
     * Registers a new user with the given details and assigns a role based on the email domain.
     * @param username The username for the new account.
     * @param email The email for the new account.
     * @param password The password for the new account.
     * @return true if registration is successful, false otherwise.
     */

    public boolean signUp(String username, String email, String password) {
        if (isEmailUsed(email)) {
            throw new BusinessLogicException("Email is already in use.");
        }

        String role = determineRoleByEmail(email);
        int userId;

        User newUser;
        switch (role) {
            case "Admin":
                userId = (adminRepository != null ? adminRepository.getAll().size() : userRepository.getAll().size()) + 1;
                newUser = new Admin(userId, username, email, password, role);
                if (adminRepository != null) { // Pentru File/DB
                    adminRepository.create((Admin) newUser);
                } else { // Pentru InMemory
                    userRepository.create(newUser);
                }
                break;
            case "Developer":
                userId = (developerRepository != null ? developerRepository.getAll().size() : userRepository.getAll().size()) + 1;
                newUser = new Developer(userId, username, email, password, role, new ArrayList<>());
                if (developerRepository != null) { // Pentru File/DB
                    developerRepository.create((Developer) newUser);
                } else { // Pentru InMemory
                    userRepository.create(newUser);
                }
                break;

            default:
                userId = (customerRepository != null ? customerRepository.getAll().size() : userRepository.getAll().size()) + 1;
                newUser = new Customer(userId, username, email, password, "Customer", 0.0f, new ArrayList<>(), new ArrayList<>(), null);
                userRepository.create(newUser); // UserRepo este folosit în toate cazurile pentru User
                break;
        }

        return true;
    }



    /**
     * Generează un ID unic pentru un utilizator, luând în considerare tipul de repository.
     * @param role Rolul utilizatorului ("Admin", "Developer", "Customer").
     * @return ID-ul unic generat.
     */
    private int generateUniqueUserId(String role) {
        if (userRepository != null) {
            // Cazul InMemory: folosim userRepository pentru a calcula ID-ul
            return userRepository.getAll().stream().mapToInt(User::getId).max().orElse(0) + 1;
        }

        // Cazurile File și Database: calculăm ID-ul pentru fiecare tip de utilizator
        int maxId = 0;
        switch (role) {
            case "Admin":
                for (Admin admin : adminRepository.getAll()) {
                    maxId = Math.max(maxId, admin.getId());
                }
                break;
            case "Developer":
                for (Developer developer : developerRepository.getAll()) {
                    maxId = Math.max(maxId, developer.getId());
                }
                break;
            case "Customer":
                for (Customer customer : customerRepository.getAll()) {
                    maxId = Math.max(maxId, customer.getId());
                }
                break;
        }
        return maxId + 1; // Returnăm următorul ID disponibil
    }


    /**
     * Authenticates a user with their email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return true if login is successful, false otherwise.
     */

    public boolean logIn(String email, String password) {
        List<User> users = new ArrayList<>(userRepository.getAll());

        // Pentru File/DB, verificăm repository-urile specifice
        if (adminRepository != null) {
            users.addAll(adminRepository.getAll());
        }
        if (developerRepository != null) {
            users.addAll(developerRepository.getAll());
        }

        if (customerRepository != null) {
            users.addAll(customerRepository.getAll());
        }

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                loggedInUser = u;
                System.out.println("Successful authentication for user: " + loggedInUser.getUsername());
                return true;
            }
        }

        throw new BusinessLogicException("Wrong email or password.");
    }




    /**
     * Combines all users from the repositories into a single list.
     * @return A list of all users.
     */
    private List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();

        if (userRepository != null) { // Pentru InMemory
            allUsers.addAll(userRepository.getAll());
        }

        if (adminRepository != null) { // Pentru File/Database
            allUsers.addAll(adminRepository.getAll());
        }

        if (developerRepository != null) { // Pentru File/Database
            allUsers.addAll(developerRepository.getAll());
        }

        if (customerRepository != null) { // Pentru File/Database
            allUsers.addAll(customerRepository.getAll());
        }

        return allUsers;
    }


    /**
     * Logs out the currently logged-in user.
     * @return true if the user was logged out successfully, false if no user was logged in.
     */

    public boolean logOut() {
        if (loggedInUser != null) {
            loggedInUser = null;
            return true;
        }
        throw new BusinessLogicException("No user is logged in to log out.");
    }


    /**
     * Checks if a given email is already in use by an existing user.
     * @param email The email to check.
     * @return true if the email is in use, false otherwise.
     */

    private boolean isEmailUsed(String email) {

        if (userRepository != null) { // Pentru InMemoryRepository
            for (User user : userRepository.getAll()) {
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }
        } else { // Pentru FileRepository sau DatabaseRepository
            for (Admin admin : adminRepository.getAll()) {
                if (admin.getEmail().equals(email)) {
                    return true;
                }
            }
            for (Developer developer : developerRepository.getAll()) {
                if (developer.getEmail().equals(email)) {
                    return true;
                }
            }

             for (Customer customer : customerRepository.getAll()) {
                 if (customer.getEmail().equals(email)) {
                     return true;
                 }
             }
        }
        return false;
    }

    /**
     * Determines the role of a user based on their email domain.
     * @param email The email of the user.
     * @return A string representing the role, either "Admin", "Developer", or "Customer".
     */

    private String determineRoleByEmail(String email) {
        if (email.endsWith("@adm.com")) {
            return "Admin";
        } else if (email.endsWith("@dev.com")) {
            return "Developer";
        } else {
            return "Customer";
        }
    }

    /**
     * Deletes the currently logged-in user's account.
     * @return true if the account was deleted, false otherwise.
     */

    public boolean deleteAccount() {
        if (loggedInUser != null) {
            switch (loggedInUser.getRole()) {
                case "Admin":
                    if (adminRepository != null) {
                        adminRepository.delete(loggedInUser.getId());
                    } else {
                        throw new BusinessLogicException("Admin repository is not initialized.");
                    }
                    break;
                case "Developer":
                    if (developerRepository != null) {
                        developerRepository.delete(loggedInUser.getId());
                    } else {
                        throw new BusinessLogicException("Developer repository is not initialized.");
                    }
                    break;
                case "Customer":
                    if (userRepository != null) {
                        Customer customerToDelete = (Customer) loggedInUser;
                        ShoppingCart shoppingCart = customerToDelete.getShoppingCart();
                        if (shoppingCart != null) {
                            shoppingCart.getListOfGames().clear();
                        }
                        if (customerToDelete.getReviews() != null) {
                            customerToDelete.getReviews().clear();
                        }
                        if (customerToDelete.getGamesLibrary() != null) {
                            customerToDelete.getGamesLibrary().clear();
                        }
                        userRepository.delete(customerToDelete.getId());
                    } else {
                        throw new BusinessLogicException("User repository is not initialized.");
                    }
                    break;
                default:
                    if (userRepository != null) {
                        userRepository.delete(loggedInUser.getId());
                    } else {
                        throw new BusinessLogicException("User repository is not initialized.");
                    }
                    break;
            }
            loggedInUser = null;
            return true;
        }
        throw new BusinessLogicException("No user is logged in to delete.");
    }

    /**
     * Deletes any user account by email (admin-only function).
     * @param email The email of the account to delete.
     * @return true if the account was deleted, false otherwise.
     */

    public boolean deleteAnyAccount(String email) {
        User userToDelete = null;
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete == null) {
            throw new BusinessLogicException("User with the given email does not exist.");
        }

        deleteUserByRole(userToDelete);
        return true;
    }


    /**
     * Helper method to delete a user based on their role.
     * @param user The user to delete.
     */
    private void deleteUserByRole(User user) {
        if (user == null) {
            throw new BusinessLogicException("User is null. Cannot delete.");
        }

        switch (user.getRole()) {
            case "Admin" -> {
                adminRepository.delete(user.getId());
            }
            case "Developer" -> {
                developerRepository.delete(user.getId());
            }
            case "Customer" -> {
                customerRepository.delete(user.getId());
            }
            default -> throw new BusinessLogicException("Invalid user role: " + user.getRole());
        }

        // If we are using InMemory, also delete from userRepository
        if (userRepository != null) {
            userRepository.delete(user.getId());
        }
    }


    /**
     * Retrieves the currently logged-in user.
     * @return The logged-in user, or null if no user is logged in.
     */

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
