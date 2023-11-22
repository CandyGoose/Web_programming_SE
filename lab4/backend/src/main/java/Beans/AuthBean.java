package Beans;

import DBModels.UserDB;
import Exceptions.LoginException;
import Exceptions.RefreshTokenException;
import Exceptions.RegistrationException;
import Models.LoginRequest;
import Models.RefreshTokenRequest;
import Models.RegistrationRequest;
import Models.Tokens;
import Utils.CryptUtils;
import Utils.JWTUtils;
import lombok.extern.java.Log;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * The AuthBean class manages user authentication and registration within the system.
 * It provides functionalities for user login, token generation, refreshing tokens, and registration.
 * Uses JWT for token creation and manages user data in the database.
 */
@Stateless
@Log
public class AuthBean {
    /**
     * Entity manager to interact with the database.
     */
    @PersistenceContext(unitName = "persistence-unit")
    private EntityManager entityManager;

    /**
     * Performs user login based on the provided login request.
     *
     * @param request The login request containing username and password.
     * @return Tokens object containing access and refresh tokens.
     * @throws LoginException if user is not found or password is incorrect.
     */
    public Tokens login(LoginRequest request) throws LoginException {
        UserDB user = findUserByUsername(request.getUsername())
                .orElseThrow(() -> {
                    String message = "User with this username not found. Try to register";
                    return LoginException.userNotFound(message);
                });

        if (!CryptUtils.checkPassword(request.getPassword(), user.getPassword()))
            throw LoginException.wrongPassword("Wrong password");

        String accessToken = JWTUtils.tokenForUser(user);
        String refreshToken = UUID.randomUUID().toString();

        user.setRefreshToken(refreshToken);

        return new Tokens(accessToken, refreshToken);
    }

    /**
     * Generates new tokens based on the refresh token provided.
     *
     * @param request The refresh token request.
     * @return Tokens object containing new access and refresh tokens.
     * @throws RefreshTokenException if the refresh token is invalid.
     */
    public Tokens refreshToken(RefreshTokenRequest request) throws RefreshTokenException {
        UserDB user = findByRefreshToken(request.getRefresh()).orElseThrow(RefreshTokenException::new);
        String accessToken = JWTUtils.tokenForUser(user);
        String refreshToken = request.getRefresh();
        return new Tokens(accessToken, refreshToken);
    }

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param request The registration request containing username and password.
     * @throws RegistrationException if registration fails due to invalid data or existing username.
     */
    public void registration(RegistrationRequest request) throws RegistrationException {
        AuthBean.validate(request);

        if (findUserByUsername(request.getUsername()).isPresent())
            throw RegistrationException.userAlreadyExist("User with this username already exist. Try to login");

        String hashedPassword = CryptUtils.hashPassword(request.getPassword1());

        UserDB user = UserDB.builder()
                .username(request.getUsername())
                .password(hashedPassword)
                .build();

        entityManager.persist(user);
    }

    /**
     * Finds a user in the database based on the provided username.
     *
     * @param username The username to search for.
     * @return Optional containing the user if found, else empty.
     */
    private Optional<UserDB> findUserByUsername(String username) {
        try {
            Query namedQuery = entityManager.createNamedQuery("UserDB.findByUsername");
            namedQuery.setParameter("username", username);
            UserDB user = (UserDB) namedQuery.getSingleResult();
            return Optional.of(user);
        } catch (PersistenceException exception) {
            return Optional.empty();
        }
    }

    /**
     * Finds a user in the database based on the provided refresh token.
     *
     * @param refreshToken The refresh token to search for.
     * @return Optional containing the user if found, else empty.
     */
    private Optional<UserDB> findByRefreshToken(String refreshToken) {
        try {
            Query namedQuery = entityManager.createNamedQuery("UserDB.findByRefreshToken");
            namedQuery.setParameter("refreshToken", refreshToken);
            UserDB user = (UserDB) namedQuery.getSingleResult();
            return Optional.of(user);
        } catch (PersistenceException exception) {
            return Optional.empty();
        }
    }

    /**
     * Validates the RegistrationRequest object for registration purposes.
     *
     * @param request RegistrationRequest object to be validated.
     * @throws RegistrationException when validation fails due to missing or invalid data.
     */
    private static void validate(RegistrationRequest request) throws RegistrationException {
        if (Objects.isNull(request.getUsername()))
            throw RegistrationException.notEnoughData("Hasn't username");

        if (Objects.isNull(request.getPassword1()))
            throw RegistrationException.notEnoughData("Hasn't password");

        if (Objects.isNull(request.getPassword2()))
            throw RegistrationException.notEnoughData("Hasn't password confirmation");

        if (request.getUsername().isEmpty())
            throw RegistrationException.invalidData("Username is empty");

        if (request.getPassword1().isEmpty())
            throw RegistrationException.invalidData("Password is empty");

        if (request.getPassword2().isEmpty())
            throw RegistrationException.invalidData("Password confirmation is empty");

        if(!Objects.equals(request.getPassword1(), request.getPassword2()))
            throw RegistrationException.passwordsNotEqual("Password and password confirmation must equal");
    }
}
