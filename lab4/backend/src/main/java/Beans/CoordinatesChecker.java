package Beans;

import Models.Coordinates;

import javax.ejb.Stateless;
import java.math.BigDecimal;

/**
 * CoordinatesChecker performs geometric checks based on provided coordinates.
 */
@Stateless
public class CoordinatesChecker {
    /**
     * Checks if the given coordinates fall within specified geometric shapes.
     *
     * @param coordinates The coordinates to be checked.
     * @return True if the coordinates fall within the specified shapes; otherwise, false.
     */
    public Boolean check(Coordinates coordinates) {
        BigDecimal x = new BigDecimal(String.valueOf(coordinates.getX()).replace(',', '.'));
        BigDecimal y = new BigDecimal(String.valueOf(coordinates.getY()).replace(',', '.'));
        BigDecimal r = new BigDecimal(String.valueOf(coordinates.getR()).replace(',', '.'));

        BigDecimal halfR = r.divide(BigDecimal.valueOf(2));

        if (r.compareTo(BigDecimal.ZERO) < 0) {
            boolean circle = x.compareTo(BigDecimal.ZERO) <= 0
                    && y.compareTo(BigDecimal.ZERO) <= 0
                    && x.pow(2).add(y.pow(2)).compareTo(r.pow(2)) <= 0;

            boolean triangle = x.compareTo(BigDecimal.ZERO) >= 0
                    && y.compareTo(BigDecimal.ZERO) >= 0
                    && (BigDecimal.valueOf(-2).multiply(y)).compareTo(r.add(x)) >= 0;

            boolean rectangle = x.compareTo(BigDecimal.ZERO) >= 0
                    && y.compareTo(BigDecimal.ZERO) <= 0
                    && x.compareTo(BigDecimal.ZERO.subtract(halfR)) <= 0
                    && y.compareTo(r) >= 0;
            return circle || triangle || rectangle;
        } else {
            boolean circle = x.compareTo(BigDecimal.ZERO) >= 0
                    && y.compareTo(BigDecimal.ZERO) >= 0
                    && x.pow(2).add(y.pow(2)).compareTo(r.pow(2)) <= 0;

            boolean triangle = x.compareTo(BigDecimal.ZERO) <= 0
                    && y.compareTo(BigDecimal.ZERO) <= 0
                    && (BigDecimal.valueOf(-2).multiply(y)).compareTo(r.add(x)) <= 0;

            boolean rectangle = x.compareTo(BigDecimal.ZERO) <= 0
                    && y.compareTo(BigDecimal.ZERO) >= 0
                    && x.compareTo(BigDecimal.ZERO.subtract(halfR)) >= 0
                    && y.compareTo(r) <= 0;
            return circle || triangle || rectangle;
        }
    }
}
