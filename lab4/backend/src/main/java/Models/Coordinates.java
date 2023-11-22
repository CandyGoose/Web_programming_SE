package Models;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents coordinates with values for x, y, and r.
 */
@Data
public class Coordinates {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
}
