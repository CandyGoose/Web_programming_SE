package Models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Coordinates {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
}
