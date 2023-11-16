package Rest.CorsFilter;

import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;

@Provider
@PreMatching
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private final static String ORIGIN_HEADER = "Origin";
    private final static String OPTIONS_METHOD = "OPTIONS";

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

    private static boolean isPreflightRequest(ContainerRequestContext request) {
        String origin = request.getHeaderString(ORIGIN_HEADER);
        String method = request.getMethod();

        return Objects.nonNull(origin)
                && Objects.nonNull(method)
                && method.equalsIgnoreCase(OPTIONS_METHOD);
    }
}