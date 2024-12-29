package Model;

import java.io.Serializable;

/**
 * Abstract class representing a user in the application.
 * Users can have different roles, such as Admin, Developer, or Customer.
 */

public abstract class User implements HasId, Serializable {
    protected Integer userId;
    protected String username;
    protected String email;
    protected String password;
    protected String role;

    /**
     * Constructs a User with the specified details.
     *
     * @param userId   The unique identifier for the user.
     * @param username The username of the user.
     * @param email    The email of the user.
     * @param password The password of the user.
     * @param role     The role of the user (Admin, Developer, Customer).
     */

    public User(Integer userId, String username, String email, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}


