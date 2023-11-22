package Models;

import lombok.Data;

/**
 * Represents a request to refresh a token.
 */
@Data
public class RefreshTokenRequest {
    private String refresh;
}
