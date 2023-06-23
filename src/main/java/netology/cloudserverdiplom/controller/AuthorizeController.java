package netology.cloudserverdiplom.controller;

import netology.cloudserverdiplom.logger.LoggerClass;
import netology.cloudserverdiplom.model.AuthorizeData;
import netology.cloudserverdiplom.service.AuthorizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthorizeController {

    private final AuthorizeService authorizeService;
    private static LoggerClass logger = new LoggerClass();

    public AuthorizeController(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthorizeData authorizeData) {
       logger.writeLog("Authorize request: " + authorizeData.toString());
       return ResponseEntity.ok(authorizeService.login(authorizeData));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String login) {
        authorizeService.logout(login);
        logger.writeLog("Logout request: " + login);
        return ResponseEntity.ok("User logged out successfully.");
    }
}
