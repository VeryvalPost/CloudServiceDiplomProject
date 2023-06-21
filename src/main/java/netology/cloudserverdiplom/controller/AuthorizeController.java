package netology.cloudserverdiplom.controller;

import netology.cloudserverdiplom.model.AuthorizeData;
import netology.cloudserverdiplom.model.Token;
import netology.cloudserverdiplom.service.AuthorizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizeController {

    private final AuthorizeService authorizeService;

    public AuthorizeController(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authorizeUser(@RequestBody String login, String password) {
        AuthorizeData authorizeData = new AuthorizeData(login, password);
        return ResponseEntity.ok(authorizeService.login(authorizeData).getToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody String login) {
        authorizeService.logout(login);
        return ResponseEntity.ok("User logged out successfully.");
    }
}
