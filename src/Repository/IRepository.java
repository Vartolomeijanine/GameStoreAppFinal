package Repository;
import Model.HasId;
import java.util.List;

/**
 * Generic repository interface for basic CRUD operations.
 * @param <T> The type of objects managed by the repository.
 */

public interface IRepository<T extends HasId> {

    /**
     * Adds a new object to the repository.
     * @param obj The object to add.
     */
    void create(T obj);

    /**
     * Retrieves an object from the repository by its ID.
     * @param id The ID of the object to retrieve.
     * @return The object with the specified ID, or null if not found.
     */
    T get(Integer id);

    /**
     * Updates an existing object in the repository.
     * @param obj The object with updated information.
     */
    void update(T obj);

    /**
     * Deletes an object from the repository by its ID.
     * @param id The ID of the object to delete.
     */
    void delete(Integer id);

    /**
     * Retrieves all objects in the repository.
     * @return A list of all objects in the repository.
     */
    List<T> getAll();
}
