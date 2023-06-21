package netology.cloudserverdiplom.service;

import netology.cloudserverdiplom.model.AuthorizeData;
import netology.cloudserverdiplom.model.Token;
import netology.cloudserverdiplom.repository.AuthorizeRepo;
import netology.cloudserverdiplom.security.JWTUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService {

    private JWTUtil jwtUtil;
    private AuthorizeRepo authorizeRepo;

    public AuthorizeService(JWTUtil jwtUtil, AuthorizeRepo authorizeRepo) {
        this.jwtUtil = jwtUtil;
        this.authorizeRepo = authorizeRepo;
    }

    public Token login(AuthorizeData authorizeData) {
        String login = authorizeData.getLogin();
        String password = authorizeData.getPassword();

        if (authorizeRepo.isDuplicate(login)) {
            return authorizeRepo.getAuthDataByLogin(login);
        } else {
            String authToken = jwtUtil.generateToken(login, password);
            Token token = new Token(authToken);
            authorizeRepo.putAuthData(login, token);
            return token;
        }
    }
    public void logout(String login) {
        authorizeRepo.removeAuthData(login);
    }
}