package data;

/**
 * The Coordinates class represents a set of coordinates with an 'x' and 'y' value, along with an 'r' value.
 */
public class Coordinates {

    private final String x;
    private final String y;
    private final String r;

    /**
     * Creates a new Coordinates object with the specified 'x', 'y', and 'r' values.
     *
     * @param x The 'x' coordinate value as a string.
     * @param y The 'y' coordinate value as a string.
     * @param r The 'r' value representing a radius.
     */
    public Coordinates(String x, String y, String r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    /**
     * Returns a string representation of the coordinates in the format "(x; y; r)".
     *
     * @return The string representation of the coordinates.
     */
    @Override
    public String toString() {
        return String.format("(%s; %s; %s)", x, y, r);
    }

    /**
     * Gets the 'x' coordinate value as a string.
     *
     * @return The 'x' coordinate value.
     */
    public String getX() {
        return x;
    }

    /**
     * Gets the 'y' coordinate value as a string.
     *
     * @return The 'y' coordinate value.
     */
    public String getY() {
        return y;
    }

    /**
     * Gets the 'r' value representing a radius.
     *
     * @return The 'r' value.
     */
    public String getR() {
        return r;
    }
}
