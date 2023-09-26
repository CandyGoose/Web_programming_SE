package data;

import java.io.Serializable;
import java.util.Date;

/**
 * The Result class represents a result of some operation, including the coordinates,
 * execution time and result type.
 */
public class Result implements Serializable {
    private final Coordinates coordinates;
    private final Date currTime;
    private final double execTime;
    private final Type result;

    /**
     * Creates a new Result object with the specified coordinates, current time, execution time, and result type.
     *
     * @param coordinates The coordinates associated with the result.
     * @param currTime    The timestamp representing the current time when the result was recorded.
     * @param execTime    The execution time in milliseconds for the operation that generated this result.
     * @param result      The type of the result (e.g., HIT, FAIL, etc.).
     */
    public Result(Coordinates coordinates,
                  Date currTime,
                  double execTime,
                  Type result) {
        this.coordinates = coordinates;
        this.currTime = currTime;
        this.execTime = execTime;
        this.result = result;
    }

    /**
     * Gets the coordinates associated with this result.
     *
     * @return The coordinates of the result.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the timestamp representing the current time when the result was recorded.
     *
     * @return The timestamp of the result.
     */
    public Date getCurrTime() {
        return currTime;
    }

    /**
     * Gets the execution time in milliseconds for the operation that generated this result.
     *
     * @return The execution time in milliseconds.
     */
    public double getExecTime() {
        return execTime;
    }

    /**
     * Gets the type of the result (e.g., HIT, FAIL, etc.).
     *
     * @return The type of the result.
     */
    public Type getResult() {
        return result;
    }

    /**
     * Enumeration representing different result types.
     */
    public enum Type {
        HIT,
        FAIL,
        FAILED_VALIDATING,
        WRONG_FORMAT,
        NONE
    }
}
