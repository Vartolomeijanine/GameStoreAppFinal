package Model;

import java.io.Serializable;

/**
 * An interface that enforces the presence of a unique identifier for objects.
 * Classes implementing this interface must provide an ID through the getId() method.
 */

public interface HasId extends Serializable {

    /**
     * Gets the unique identifier of the object.
     * @return The ID of the object.
     */

    Integer getId();
}
