package Rest.CorsFilter;

import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;

/**
 * Filter for handling Cross-Origin Resource Sharing (CORS) requests.
 */
@Provider
@PreMatching
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private final static String ORIGIN_HEADER = "Origin";
    private final static String OPTIONS_METHOD = "OPTIONS";

    /**
     * Filters the incoming request context for CORS preflight requests.
     *
     * @param requestContext The container request context.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void filter(
            ContainerRequestContext requestContext
    ) throws IOException {
        if (isPreflightRequest(requestContext)) {
            Response response = Response.ok().build();
            requestContext.abortWith(response);
            return;
        }
    }

    /**
     * Filters the outgoing response context to add CORS headers.
     *
     * @param requestContext  The container request context.
     * @param responseContext The container response context.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) throws IOException {
        String origin = requestContext.getHeaderString(ORIGIN_HEADER);

        if (origin == null)
            origin = "*";

        responseContext.getHeaders().add(
                "Access-Control-Allow-Credentials",
                "true");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Private-Network",
                "true");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Headers",
                "Origin, Content-Type, Accept, Authorization");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Origin", origin);
    }

    /**
     * Checks if the request is a CORS preflight request.
     *
     * @param request The container request context.
     * @return True if the request is a preflight request, otherwise false.
     */
    private static boolean isPreflightRequest(ContainerRequestContext request) {
        String origin = request.getHeaderString(ORIGIN_HEADER);
        String method = request.getMethod();

        return Objects.nonNull(origin)
                && Objects.nonNull(method)
                && method.equalsIgnoreCase(OPTIONS_METHOD);
    }
}