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

@Stateless
@Log
public class AuthBean {
    @PersistenceContext(unitName = "persistence-unit")
    private EntityManager entityManager;

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

    public Tokens refreshToken(RefreshTokenRequest request) throws RefreshTokenException {
        UserDB user = findByRefreshToken(request.getRefresh()).orElseThrow(RefreshTokenException::new);
        String accessToken = JWTUtils.tokenForUser(user);
        String refreshToken = request.getRefresh();
        return new Tokens(accessToken, refreshToken);
    }

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
