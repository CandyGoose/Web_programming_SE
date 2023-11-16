package Utils;

import org.mindrot.jbcrypt.BCrypt;

public class CryptUtils {
    public static String hashPassword(String rawPassword) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(rawPassword, salt);
    }

    public static Boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
