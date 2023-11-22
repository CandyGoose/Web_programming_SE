package Models;

import lombok.Data;

/**
 * Represents a registration request with username and passwords.
 */
@Data
public class RegistrationRequest {
    private String username;
    private String password1;
    private String password2;
}
