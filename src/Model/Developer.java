package Model;

import java.util.List;

/**
 * Represents a Developer user, who can publish and manage games in the application.
 */

public class Developer extends User{
    private List<Game> publishedGames;

    /**
     * Constructs a Developer user with the specified details.
     * @param userId         The unique identifier of the developer.
     * @param username       The username of the developer.
     * @param email          The email of the developer.
     * @param password       The password of the developer.
     * @param role           The role of the developer ("Developer").
     * @param publishedGames The list of games published by the developer.
     */

    public Developer(Integer userId, String username, String email, String password, String role, List<Game> publishedGames) {
        super(userId, username, email, password, role);
        this.publishedGames = publishedGames;
    }

    /**
     * Adds a game to the list of published games for the developer.
     * @param game The game to add.
     */

    public void addPublishedGame(Game game) {
        this.publishedGames.add(game);
    }

    public List<Game> getPublishedGames() {
        return publishedGames;
    }

    public void setPublishedGames(List<Game> publishedGames) {
        this.publishedGames = publishedGames;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", publishedGames=" + publishedGames +
                '}';
    }

    @Override
    public Integer getId() {
        return userId;
    }
}
