package data;

import java.io.Serializable;
import java.util.Date;

public class Result implements Serializable {
    private final Coordinates coordinates;
    private final Date currTime;
    private final double execTime;
    private final Type result;

    public Result(Coordinates coordinates,
                  Date currTime,
                  double execTime,
                  Type result) {
        this.coordinates = coordinates;
        this.currTime = currTime;
        this.execTime = execTime;
        this.result = result;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCurrTime() {
        return currTime;
    }

    public double getExecTime() {
        return execTime;
    }

    public Type getResult() {
        return result;
    }

    public enum Type {
        HIT,
        FAIL,
        FAILED_VALIDATING,
        WRONG_FORMAT,
        NONE
    }
}
