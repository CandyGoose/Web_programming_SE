package database;

import beans.Result;

import java.util.List;

/**
 * This interface defines the contract for interacting with a data store for results.
 */
public interface ResultInterface {
    /**
     * Saves the given result to the data store.
     *
     * @param result The result to be saved.
     */
    void save(Result result);

    /**
     * Clears all results from the data store.
     *
     * @return true if the operation was successful, false otherwise.
     */
    boolean clear();

    /**
     * Retrieves a list of all results from the data store.
     *
     * @return A list of Result objects representing stored results.
     */
    List<Result> getAll();
}
