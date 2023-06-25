package netology.cloudserverdiplom.controller;

import netology.cloudserverdiplom.error.AuthorizeError;
import netology.cloudserverdiplom.error.FileError;
import netology.cloudserverdiplom.error.TokenError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvise {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvise.class);

    @ExceptionHandler(AuthorizeError.class)
    public ResponseEntity handleErrorAuthorize(AuthorizeError e) {
        logger.error("Authorize error: " + e.getMessage());
        return ResponseEntity.badRequest().body(new AuthorizeError("Authorize error: wrong login or password"));
    }

    @ExceptionHandler(FileError.class)
    public ResponseEntity handleErrorFile(FileError e) {
        logger.error("File error: " + e.getMessage());
        return ResponseEntity.status(500).body(new FileError("File error "));
    }

    @ExceptionHandler(TokenError.class)
    public ResponseEntity handleErrorToken(TokenError e) {
        logger.error("Token error" + e.getMessage());
        return ResponseEntity.status(400).body(new TokenError("Token error "));
    }
}
