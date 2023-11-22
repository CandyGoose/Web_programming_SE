package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a response from a check operation.
 */
@Data
@AllArgsConstructor
public class CheckResponse {
    private Boolean success;
}
