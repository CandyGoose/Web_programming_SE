package database;

import beans.Result;
import java.util.List;

import javax.persistence.*;
import javax.enterprise.inject.Default;

/**
 * This class provides an implementation of the ResultInterface for interacting with a data store using JPA (Java Persistence API).
 */
@Default
public class ResultInterfaceImplementation implements ResultInterface {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ResultUnit");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    /**
     * Saves the given result to the data store.
     *
     * @param result The result to be saved.
     */
    @Override
    public void save(Result result) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(result);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    /**
     * Clears all results from the data store.
     *
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public boolean clear() {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.createQuery("delete from Result").executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    /**
     * Retrieves a list of all results from the data store, ordered by result ID.
     *
     * @return A list of Result objects representing stored results.
     */
    public List<Result> getAll() {
        return entityManager.createQuery("select result from Result result ORDER BY result.id", Result.class).getResultList();
    }
}
