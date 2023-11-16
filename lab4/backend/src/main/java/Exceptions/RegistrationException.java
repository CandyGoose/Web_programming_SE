package Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RegistrationException extends Exception {
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

    public static RegistrationException notEnoughData(String message) {
        return new RegistrationException(Code.NOT_ENOUGH_DATA, message);
    }

    public static RegistrationException invalidData(String message) {
        return new RegistrationException(Code.INVALID_DATA, message);
    }

    public static RegistrationException passwordsNotEqual(String message) {
        return new RegistrationException(Code.PASSWORDS_NOT_EQUAL, message);
    }

    public static RegistrationException userAlreadyExist(String message) {
        return new RegistrationException(Code.USER_ALREADY_EXIST, message);
    }
}
