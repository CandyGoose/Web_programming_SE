package Utils;

import DBModels.UserDB;
import Rest.AuthFilter.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for generating and verifying JWT tokens.
 */
public class JWTUtils {
    private final static String USER_ID_CLAIM = "user_id";
    private final static String USERNAME_CLAIM = "username";
    private final static String SECRET_KEY = "wSw3W3NU2c6cjogv6OaqvEtfJg8aF5D";
    private final static long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(2);

    /**
     * Generates a JWT token for the provided user.
     *
     * @param user The user for whom the token is generated.
     * @return The generated JWT token.
     */
    public static String tokenForUser(UserDB user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .claim(USER_ID_CLAIM, user.getId().toString())
                .claim(USERNAME_CLAIM, user.getUsername())
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(SECRET_KEY))
                .compact();
    }

    /**
     * Verifies and parses a JWT token, returning the user principal if valid.
     *
     * @param token The JWT token to verify.
     * @return Optional containing the user principal if the token is valid, otherwise empty.
     */
    public static Optional<UserPrincipal> verify(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.decode(SECRET_KEY))
                    .parseClaimsJws(token)
                    .getBody();

            Date now = new Date();
            Date expiration = claims.getExpiration();
            if (now.compareTo(expiration) > 0) {
                return Optional.empty();
            }

            String username = (String) claims.get(USERNAME_CLAIM);
            String userIDString = (String) claims.get(USER_ID_CLAIM);
            Long userID = Long.valueOf(userIDString);

            UserPrincipal userPrincipal = new UserPrincipal(userID, username);

            return Optional.of(userPrincipal);
        } catch (SignatureException ignored) {
            return Optional.empty();
        }
    }
}
