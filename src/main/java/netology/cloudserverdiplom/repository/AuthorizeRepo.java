package netology.cloudserverdiplom.repository;

import netology.cloudserverdiplom.entity.User;
import netology.cloudserverdiplom.model.Token;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AuthorizeRepo {
    private final Map<String,Token> authorizeRepo = new ConcurrentHashMap<>();

    public void putAuthData(String login, Token authToken){
        authorizeRepo.put(login, authToken);
    }

    public Token getAuthDataByLogin(String login){
        return authorizeRepo.get(login);
    }

    public void removeAuthData(String login){
        authorizeRepo.remove(login);
    }

    public boolean isDuplicate(String login) {
        return authorizeRepo.containsKey(login);
    }
}
