package netology.cloudserverdiplom.service;

import netology.cloudserverdiplom.entity.User;
import netology.cloudserverdiplom.error.AuthorizeError;
import netology.cloudserverdiplom.logger.LoggerClass;
import netology.cloudserverdiplom.model.AuthorizeData;
import netology.cloudserverdiplom.model.Token;
import netology.cloudserverdiplom.repository.UserRepo;
import netology.cloudserverdiplom.security.JWTUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService {

    private JWTUtil jwtUtil;
    private UserRepo userRepo;
    private static LoggerClass logger = new LoggerClass();


    public AuthorizeService(JWTUtil jwtUtil, UserRepo userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    public Token login(AuthorizeData authorizeData) {
        String login = authorizeData.getLogin();
        String password = authorizeData.getPassword();
        User user = userRepo.findByLogin(login);

        if (user.getPassword().equals(password)) {
            String authToken = jwtUtil.generateToken(login, password);
            final Token token = new Token(authToken);
            user.setAuthToken(authToken);
            userRepo.saveAndFlush(user);
            logger.writeLog("Successful authorization: " + login);
            return token;

        } else throw new AuthorizeError("Wrong login or password");
    }

    public void logout(String login) {
        User user = userRepo.findByLogin(login);
        user.setAuthToken("null");
        userRepo.saveAndFlush(user);
        logger.writeLog("Successful logout: " + login);
    }
}