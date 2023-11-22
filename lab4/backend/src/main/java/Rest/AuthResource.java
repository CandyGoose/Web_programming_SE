package Rest;

import Beans.AuthBean;
import Exceptions.LoginException;
import Exceptions.RefreshTokenException;
import Exceptions.RegistrationException;
import Models.*;
import Models.Error;
import lombok.extern.java.Log;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource handling authentication operations.
 */
@Log
@Stateless
@Path("/auth")
public class AuthResource {
    @EJB
    private AuthBean authBean;

    /**
     * Handles the login operation.
     *
     * @param request The login request.
     * @return Response containing tokens or error.
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        try {
            log.info("/login");
            Tokens tokens = authBean.login(request);
            return Response.ok().entity(tokens).build();
        } catch (LoginException exception) {
            Error error = AuthResource.transform(exception);
            return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
        }
    }

    /**
     * Handles the token refresh operation.
     *
     * @param request The refresh token request.
     * @return Response containing new tokens or error.
     */
    @POST
    @Path("/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshToken(RefreshTokenRequest request) {
        try {
            log.info("/refresh");
            Tokens tokens = authBean.refreshToken(request);
            return Response.ok().entity(tokens).build();
        } catch (RefreshTokenException exception) {
            Error error = AuthResource.transform(exception);
            return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
        }
    }

    /**
     * Handles the user registration operation.
     *
     * @param request The registration request.
     * @return Response indicating success or error.
     */
    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration(RegistrationRequest request) {
        try {
            log.info("/registration");
            authBean.registration(request);
            return Response.ok().build();
        } catch (RegistrationException exception) {
            Error error = AuthResource.transform(exception);
            return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
        }
    }

    /**
     * Transforms a LoginException into an Error object.
     *
     * @param exception The LoginException to transform.
     * @return Error object containing error code and message.
     */
    private static Error transform(LoginException exception) {
        String code;
        switch (exception.getCode()) {
            case USER_NOT_FOUND:
                code = "LOGIN_USER_NOT_FOUND";
                break;
            case WRONG_PASSWORD:
                code = "LOGIN_WRONG_PASSWORD";
                break;
            default:
                code = "LOGIN_UNKNOWN";
                break;
        }

        return new Error(code, exception.getMessage());
    }

    /**
     * Transforms a RegistrationException into an Error object.
     *
     * @param exception The RegistrationException to transform.
     * @return Error object containing error code and message.
     */
    private static Error transform(RegistrationException exception) {
        String code;
        switch (exception.getCode()) {
            case NOT_ENOUGH_DATA:
                code = "REGISTRATION_NOT_ENOUGH_DATA";
                break;
            case INVALID_DATA:
                code = "REGISTRATION_INVALID_DATA";
                break;
            case PASSWORDS_NOT_EQUAL:
                code = "REGISTRATION_PASSWORDS_NOT_EQUAL";
                break;
            case USER_ALREADY_EXIST:
                code = "REGISTRATION_USER_ALREADY_EXIST";
                break;
            default:
                code = "REGISTRATION_UNKNOWN";
                break;
        }
        return new Error(code, exception.getMessage());
    }

    /**
     * Transforms a RefreshTokenException into an Error object.
     *
     * @param exception The RefreshTokenException to transform.
     * @return Error object containing error code and message.
     */
    private static Error transform(RefreshTokenException exception) {
        return new Error("REFRESH_COMMON", null);
    }
}