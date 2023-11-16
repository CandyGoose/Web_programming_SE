package Models;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password1;
    private String password2;
}
