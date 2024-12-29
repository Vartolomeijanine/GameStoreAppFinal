package Repository;

import Model.HasId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of IRepository for managing objects in a HashMap.
 * This class provides basic CRUD operations for objects implementing HasId.
 * @param <T> The type of objects managed by the repository.
 */

public class InMemoryRepository<T extends HasId> implements IRepository<T> {
    private final Map<Integer,T> data = new HashMap<>();

    /**
     * Adds a new object to the repository if it doesn't already exist.
     *
     * @param obj The object to add.
     * @throws IllegalArgumentException if an object with the same ID already exists.
     */

    @Override
    public void create(T obj) {
        if(data.putIfAbsent(obj.getId(), obj) != null) {
            throw new IllegalArgumentException("Object already exists");
        }
    }

    /**
     * Retrieves an object by its ID.
     * @param id The ID of the object.
     * @return The object with the specified ID, or null if not found.
     */

    @Override
    public T get(Integer id) {
        return data.get(id);
    }

    /**
     * Updates an existing object in the repository.
     *
     * @param obj The object with updated information.
     */

    @Override
    public void update(T obj) {
        data.replace(obj.getId(), obj);
    }

    /**
     * Deletes an object from the repository by its ID.
     *
     * @param id The ID of the object to delete.
     */

    @Override
    public void delete(Integer id) {
        data.remove(id);
    }

    /**
     * Retrieves all objects in the repository.
     *
     * @return A list of all objects in the repository.
     */

    @Override
    public List<T> getAll() {
        return data.values().stream().toList();
    }
}
