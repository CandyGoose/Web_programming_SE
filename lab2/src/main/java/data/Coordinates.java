package data;

import java.math.BigDecimal;

public class Coordinates {
    private final BigDecimal x;
    private final BigDecimal y;
    private final int r;

    public Coordinates(BigDecimal x, BigDecimal y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public String toString() {
        return String.format("(%s; %s; %s)", x, y, r);
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public int getR() {
        return r;
    }
}
