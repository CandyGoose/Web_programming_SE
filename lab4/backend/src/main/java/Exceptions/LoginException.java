package Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents an exception related to login errors.
 */
@AllArgsConstructor
public class LoginException extends Exception {
    /**
     * Enum representing different error codes for login exceptions.
     */
    public enum Code {
        USER_NOT_FOUND,
        WRONG_PASSWORD,
    }

    @Getter
    private final Code code;

    @Getter
    private final String message;

    /**
     * Creates a new LoginException for user not found.
     *
     * @param message Error message indicating user not found.
     * @return LoginException instance for user not found.
     */
    public static LoginException userNotFound(String message) {
        return new LoginException(Code.USER_NOT_FOUND, message);
    }

    /**
     * Creates a new LoginException for wrong password.
     *
     * @param message Error message indicating wrong password.
     * @return LoginException instance for wrong password.
     */
    public static LoginException wrongPassword(String message) {
        return new LoginException(Code.WRONG_PASSWORD, message);
    }
}
