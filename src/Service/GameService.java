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
     * Adds a new game to the repository, assigning it a unique ID.
     *
     * @param game The game to add.
     */
    public void addGame(Game game) {
        if (game.getId() == null) {
            Integer nextId = generateNextId();
            game.setGameId(nextId);
        }

        List<Game> allGames = gameRepository.getAll();
        for (Game existingGame : allGames) {
            if (existingGame.getGameName().equalsIgnoreCase(game.getGameName())) {
                throw new BusinessLogicException("A game with the name '" + game.getGameName() + "' already exists.");
            }
        }

        try {
            gameRepository.create(game);
            System.out.println("Game added successfully: " + game.getGameName());
        } catch (IllegalArgumentException e) {
            throw new BusinessLogicException("Error while adding game: " + e.getMessage());
        }
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param gameId The ID of the game to retrieve.
     * @return The game if found, or null otherwise.
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
     */
    public List<Game> getAllGames() {
        List<Game> games = gameRepository.getAll();
        if (games.isEmpty()) {
            throw new BusinessLogicException("No games available.");
        }
        return games;
    }

    /**
     * Generates the next unique ID for a new game.
     *
     * @return The next available ID.
     */
    private Integer generateNextId() {
        List<Game> allGames = gameRepository.getAll();

        if (allGames.isEmpty()) {
            return 1;
        }

        Integer maxId = allGames.stream()
                .map(Game::getId)
                .max(Integer::compareTo)
                .orElse(0);
        return maxId + 1;
    }
}

