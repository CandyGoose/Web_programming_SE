package Utils;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Optional;

/**
 * Utility class for extracting JWT token from HTTP headers.
 */
public class HTTPHeaderExtractor {
    private final static String TOKEN_PREFIX = "Bearer";

    /**
     * Extracts the JWT token from the provided HTTP headers.
     *
     * @param headers The HTTP headers containing authorization information.
     * @return Optional containing the extracted JWT token, or empty if not found.
     */
    public static Optional<String> extractJWT(MultivaluedMap<String, String> headers) {
        try {
            String authorizationHeader = headers
                    .getFirst(HttpHeaders.AUTHORIZATION);
            return Optional
                    .of(authorizationHeader)
                    .filter(header -> { return header.startsWith(TOKEN_PREFIX); })
                    .map(header -> { return header.substring(TOKEN_PREFIX.length()).trim(); });
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
