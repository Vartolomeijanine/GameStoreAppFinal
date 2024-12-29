package Service;

import Model.Developer;
import Model.Game;
import Repository.IRepository;
import Exception.BusinessLogicException;

import java.util.List;

/**
 * Service class for developer-specific functions, such as publishing and modifying games.
 */
public class DeveloperService {
    private final IRepository<Game> gameRepository;
    private final IRepository<Developer> developerRepository;
    private Developer loggedInDeveloper;

    /**
     * Constructs the DeveloperService with game and developer repositories.
     *
     * @param gameRepository       The repository for managing games.
     * @param developerRepository  The repository for managing developers.
     */
    public DeveloperService(IRepository<Game> gameRepository, IRepository<Developer> developerRepository) {
        this.gameRepository = gameRepository;
        this.developerRepository = developerRepository;
    }

    /**
     * Sets the logged-in developer.
     *
     * @param developer The developer to set as logged in.
     */
    public void setDeveloper(Developer developer) {
        this.loggedInDeveloper = developer;
    }

    /**
     * Publishes a new game by the developer.
     *
     * @param game The game to publish.
     * @return true if the game is published successfully, false otherwise.
     */
    public boolean publishGame(Game game) {
        if (loggedInDeveloper == null) {
            throw new BusinessLogicException("You are not logged in as a developer.");
        }

        // Check if a game with the same name already exists
        List<Game> allGames = gameRepository.getAll();
        for (Game existingGame : allGames) {
            if (existingGame.getGameName().equalsIgnoreCase(game.getGameName())) {
                throw new BusinessLogicException("A game with this name already exists.");
            }
        }

        // Generate the next valid ID for the game
        int nextGameId = allGames.stream()
                .mapToInt(Game::getId)
                .max()
                .orElse(0) + 1;

        // Set the ID and associate the game with the developer
        game.setGameId(nextGameId);
        gameRepository.create(game);

        // Add the game to the developer's published games
        loggedInDeveloper.getPublishedGames().add(game);
        developerRepository.update(loggedInDeveloper);

        return true;
    }

    /**
     * Modifies an existing game if the developer owns it.
     *
     * @param gameId          The ID of the game to modify.
     * @param newName         The new name for the game.
     * @param newDescription  The new description for the game.
     * @param newGenre        The new genre for the game.
     * @param newPrice        The new price for the game.
     * @return true if the game is modified successfully, false otherwise.
     */
    public boolean modifyGame(int gameId, String newName, String newDescription, String newGenre, float newPrice) {
        if (loggedInDeveloper == null) {
            throw new BusinessLogicException("You are not logged in as a developer.");
        }

        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new BusinessLogicException("Game with ID " + gameId + " not found.");
        }

        // Check if the logged-in developer owns the game
        boolean ownsGame = loggedInDeveloper.getPublishedGames().stream()
                .anyMatch(g -> g.getId().equals(gameId));

        if (!ownsGame) {
            throw new BusinessLogicException("You don't have permission to modify this game.");
        }

        // Modify the game details
        game.setGameName(newName);
        game.setGameDescription(newDescription);
        game.setPrice(newPrice);

        // Update the game in the repository
        gameRepository.update(game);

        System.out.println("Game has been updated: " + game);
        return true;
    }

    /**
     * Retrieves the list of games published by the developer.
     *
     * @return A list of published games.
     */
    public List<Game> getPublishedGames() {
        if (loggedInDeveloper == null) {
            throw new BusinessLogicException("You are not logged in as a developer.");
        }
        return loggedInDeveloper.getPublishedGames();
    }
}

