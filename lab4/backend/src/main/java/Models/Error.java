package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an error with a specific code and message.
 */
@Data
@AllArgsConstructor
public class Error {
    private String code;
    private String message;
}
