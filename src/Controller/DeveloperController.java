package Controller;

import Model.Developer;
import Model.Game;
import Service.DeveloperService;
import java.util.Scanner;

/**
 * Controller for developer actions, such as publishing and modifying games.
 */

public class DeveloperController {
    private final DeveloperService developerService;
    private final Scanner scanner;

    /**
     * Constructs the DeveloperController with a DeveloperService instance.
     * @param developerService The DeveloperService used for developer operations.
     */

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Sets the currently logged-in developer.
     * @param developer The developer to set as logged in.
     */

    public void setDeveloper(Developer developer){
        this.developerService.setDeveloper(developer);
    }

    /**
     * Publishes a new game.
     * @param game The game to publish.
     */

    public void publishGame(Game game) {
        developerService.publishGame(game);
    }

    /**
     * Modifies an existing game's details.
     * @param gameId The ID of the game to modify.
     * @param newName The new name for the game.
     * @param newDescription The new description for the game.
     * @param newGenre The new genre for the game.
     * @param newPrice The new price for the game.
     */

    public void modifyGame(Integer gameId, String newName, String newDescription, String newGenre, Float newPrice) {
        developerService.modifyGame(gameId, newName, newDescription, newGenre, newPrice);
    }
}
