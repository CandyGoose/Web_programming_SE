package Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents a result entry containing coordinates and related details.
 */
@Data
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public class Result {
    private Long id;
    private String x;
    private String y;
    private String r;
    private String timestamp;
    private Boolean success;
}
