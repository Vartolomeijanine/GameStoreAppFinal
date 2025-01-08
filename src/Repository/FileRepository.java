package Repository;

import Model.HasId;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * File-based implementation of IRepository for managing objects using serialized files.
 * This class provides basic CRUD operations for objects implementing HasId.
 *
 * @param <T> The type of objects managed by the repository.
 */

public class FileRepository<T extends HasId> implements IRepository<T> {

    private final String filePath;

    /**
     * Constructs a FileRepository with the specified file path.
     *
     * @param filePath The path to the file where data will be stored.
     */

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Adds a new object to the repository if it doesn't already exist.
     *
     * @param obj The object to add.
     */

    @Override
    public void create(T obj) {
        doInFile(data -> data.putIfAbsent(obj.getId(), obj));
    }

    /**
     * Retrieves an object by its ID.
     *
     * @param id The ID of the object.
     * @return The object with the specified ID.
     */
    @Override
    public T get(Integer id) {
        return readDataFromFile().get(id);
    }

    /**
     * Updates an existing object in the repository.
     *
     * @param obj The object with updated information.
     */
    @Override
    public void update(T obj) {
        doInFile(data -> data.replace(obj.getId(), obj));
    }

    /**
     * Deletes an object from the repository by its ID.
     *
     * @param id The ID of the object to delete.
     */
    @Override
    public void delete(Integer id) {
        doInFile(data -> data.remove(id));
    }

    /**
     * Retrieves all objects in the repository.
     *
     * @return A list of all objects in the repository.
     */
    @Override
    public List<T> getAll() {
        return readDataFromFile().values().stream().toList();
    }

    /**
     * Performs an operation on the data stored in the file.
     *
     * @param function The function to apply to the data.
     */
    private void doInFile(Consumer<Map<Integer, T>> function) {
        Map<Integer, T> data = readDataFromFile();
        function.accept(data);
        writeDataToFile(data);
    }

    /**
     * Reads the data from the file.
     *
     * @return The data stored in the file, or an empty map if the file is empty or does not exist.
     */
    private Map<Integer, T> readDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<Integer, T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    /**
     * Writes the data to the file.
     *
     * @param data The data to write to the file.
     */
    private void writeDataToFile(Map<Integer, T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
