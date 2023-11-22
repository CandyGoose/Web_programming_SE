package Models;

import lombok.Data;

/**
 * Represents a login request with a username and password.
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
