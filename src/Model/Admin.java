package Model;

/**
 * Represents an Admin user, a specific type of User with privileges for managing the application.
 */

public class Admin extends User {

    /**
     * Constructs an Admin user with the specified attributes.
     * @param userId   The unique identifier of the admin.
     * @param username The username of the admin.
     * @param email    The email of the admin.
     * @param password The password of the admin.
     * @param role     The role of the admin ("Admin").
     */

    public Admin(Integer userId, String username, String email, String password, String role) {
        super(userId, username, email, password, role);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userId='" + userId + '\'' +
                ", username=" +  username + '\''+
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return userId;
    }
}

