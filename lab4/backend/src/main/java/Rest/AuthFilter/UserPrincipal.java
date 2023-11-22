package Rest.AuthFilter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

/**
 * Represents a user principal implementing the Principal interface.
 */
@Data
@AllArgsConstructor
public class UserPrincipal implements Principal {
    private Long id;
    private String name;
}
