package Beans;

import DBModels.ResultDB;
import DBModels.UserDB;
import Models.CheckResponse;
import Models.Coordinates;
import Models.Result;
import Rest.AuthFilter.UserPrincipal;
import lombok.extern.java.Log;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ResultsBean manages user results and coordinates check operations.
 */
@Log
@Stateless
public class ResultsBean {
    /**
     * Entity manager for database operations.
     */
    @PersistenceContext(unitName = "persistence-unit")
    private EntityManager entityManager;

    /**
     * CoordinatesChecker for validating coordinates.
     */
    @EJB
    private CoordinatesChecker coordinatesChecker;

    /**
     * Checks the provided coordinates and logs the result.
     *
     * @param coordinates   The coordinates to be checked.
     * @param userPrincipal The user principal for authentication.
     * @return CheckResponse containing the success status of the check.
     */
    public CheckResponse check(Coordinates coordinates, UserPrincipal userPrincipal) {
        try {
            Query namedQuery = entityManager.createNamedQuery("UserDB.findByID");
            namedQuery.setParameter("id", userPrincipal.getId());
            UserDB user = (UserDB) namedQuery.getSingleResult();

            Boolean success = coordinatesChecker.check(coordinates);
            log.info(success.toString());
            log.info(coordinates.toString());

            ResultDB result = ResultDB.builder()
                    .x(coordinates.getX())
                    .y(coordinates.getY())
                    .r(coordinates.getR())
                    .success(success)
                    .timestamp(LocalDateTime.now())
                    .owner(user)
                    .build();

            entityManager.persist(result);

            return new CheckResponse(success);
        } catch (NoResultException ignored) {
            return null;
        }
    }

    /**
     * Retrieves a list of results associated with the user.
     *
     * @param userPrincipal The user principal for authentication.
     * @return List of Result objects sorted by timestamp.
     */
    public List<Result> getResults(UserPrincipal userPrincipal) {
        try {
            Query namedQuery = entityManager.createNamedQuery("UserDB.findByIDWithResults");
            namedQuery.setParameter("id", userPrincipal.getId());
            UserDB user = (UserDB) namedQuery.getSingleResult();

            return user.getResults().stream()
                    .sorted(Comparator.comparing(ResultDB::getTimestamp))
                    .map(ResultsBean::transformToResult)
                    .collect(Collectors.toList());
        } catch (PersistenceException exception) {
            return new ArrayList<>();
        }
    }

    /**
     * Clears all results associated with the user.
     *
     * @param userPrincipal The user principal for authentication.
     * @return True if results were cleared successfully; otherwise, false.
     */
    public boolean clearResults(UserPrincipal userPrincipal) {
        try {
            Query namedQuery = entityManager.createNamedQuery("UserDB.findByIDWithResults");
            namedQuery.setParameter("id", userPrincipal.getId());
            UserDB user = (UserDB) namedQuery.getSingleResult();
            for (ResultDB result : user.getResults()) {
                entityManager.remove(result);
            }
            return true;
        } catch (NoResultException e) {
            return true;
        } catch (PersistenceException exception) {
            log.warning("Error clearing results: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Transforms a ResultDB object to a Result object.
     *
     * @param db The ResultDB object to be transformed.
     * @return Transformed Result object.
     */
    private static Result transformToResult(ResultDB db) {
        return new Result(
                db.getId(),
                String.valueOf(db.getX()),
                String.valueOf(db.getY()),
                String.valueOf(db.getR()),
                db.getTimestamp().toString(),
                db.getSuccess()
        );
    }
}
