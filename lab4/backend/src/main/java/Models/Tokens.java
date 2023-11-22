package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents tokens containing access and refresh tokens.
 */
@Data
@AllArgsConstructor
public class Tokens {
    private String access;
    private String refresh;
}
