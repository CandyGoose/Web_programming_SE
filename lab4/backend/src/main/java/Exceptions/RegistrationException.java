package Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents an exception related to registration errors.
 */
@AllArgsConstructor
public class RegistrationException extends Exception {
    /**
     * Enum representing different error codes for registration exceptions.
     */
    public enum Code {
        NOT_ENOUGH_DATA,
        INVALID_DATA,
        PASSWORDS_NOT_EQUAL,
        USER_ALREADY_EXIST
    }

    @Getter
    private final Code code;

    @Getter
    private final String message;

    /**
     * Creates a new RegistrationException for not enough data error.
     *
     * @param message Error message indicating not enough data.
     * @return RegistrationException instance for not enough data.
     */
    public static RegistrationException notEnoughData(String message) {
        return new RegistrationException(Code.NOT_ENOUGH_DATA, message);
    }

    /**
     * Creates a new RegistrationException for invalid data error.
     *
     * @param message Error message indicating invalid data.
     * @return RegistrationException instance for invalid data.
     */
    public static RegistrationException invalidData(String message) {
        return new RegistrationException(Code.INVALID_DATA, message);
    }

    /**
     * Creates a new RegistrationException for passwords not equal error.
     *
     * @param message Error message indicating passwords not equal.
     * @return RegistrationException instance for passwords not equal.
     */
    public static RegistrationException passwordsNotEqual(String message) {
        return new RegistrationException(Code.PASSWORDS_NOT_EQUAL, message);
    }

    /**
     * Creates a new RegistrationException for user already exists error.
     *
     * @param message Error message indicating user already exists.
     * @return RegistrationException instance for user already exists.
     */
    public static RegistrationException userAlreadyExist(String message) {
        return new RegistrationException(Code.USER_ALREADY_EXIST, message);
    }
}
