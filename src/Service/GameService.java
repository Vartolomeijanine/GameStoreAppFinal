package Service;

import Model.Game;
import Repository.IRepository;
import java.util.List;
import Exception.BusinessLogicException;

/**
 * Service class for managing games, including adding games to the repository.
 */
public class GameService {
    private final IRepository<Game> gameRepository;

    /**
     * Constructs the GameService with a game repository.
     *
     * @param gameRepository The repository for managing games.
     */
    public GameService(IRepository<Game> gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param gameId The ID of the game to retrieve.
     * @return The game if found.
     * @throws BusinessLogicException if the game with the specified ID is not found.
     */
    public Game getGameById(Integer gameId) {
        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new BusinessLogicException("Game with ID " + gameId + " not found.");
        }
        return game;
    }

    /**
     * Retrieves all games from the repository.
     *
     * @return A list of all games in the repository.
     * @throws BusinessLogicException if no games are available.
     */
    public List<Game> getAllGames() {
        List<Game> games = gameRepository.getAll();
        if (games.isEmpty()) {
            throw new BusinessLogicException("No games available.");
        }
        return games;
    }
}

