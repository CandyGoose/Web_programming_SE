package Utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt.
 */
public class CryptUtils {
    /**
     * Generates a salted and hashed password.
     *
     * @param rawPassword The raw password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(String rawPassword) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(rawPassword, salt);
    }

    /**
     * Verifies a raw password against a hashed password.
     *
     * @param rawPassword      The raw password to check.
     * @param hashedPassword   The hashed password to compare against.
     * @return True if the raw password matches the hashed password, otherwise false.
     */
    public static Boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
