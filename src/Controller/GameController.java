package Controller;

import Model.Game;
import Service.GameService;

import java.util.List;

/**
 * Controller for game-related actions, such as adding and retrieving games.
 */

public class GameController {
    private final GameService gameService;

    /**
     * Constructs the GameController with a GameService instance.
     * @param gameService The GameService used for game operations.
     */

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Adds a new game to the system.
     * @param game The game to add.
     */

    public void addGame(Game game) {
        gameService.addGame(game);
    }

    /**
     * Retrieves a game by its ID.
     * @param gameId The ID of the game to retrieve.
     * @return The game if found, or null if not.
     */

    public Game getGameById(Integer gameId) {
        return gameService.getGameById(gameId);
    }

    /**
     * Retrieves all games in the system.
     * @return A list of all games.
     */

    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }
}


