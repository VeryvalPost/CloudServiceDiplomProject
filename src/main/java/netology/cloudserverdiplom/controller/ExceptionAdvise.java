package netology.cloudserverdiplom.controller;

import netology.cloudserverdiplom.error.AuthorizeError;
import netology.cloudserverdiplom.error.FileError;
import netology.cloudserverdiplom.error.TokenError;
import netology.cloudserverdiplom.logger.LoggerClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvise {
    static LoggerClass log = new LoggerClass();

    @ExceptionHandler(AuthorizeError.class)
    public ResponseEntity handleErrorAuthorize(AuthorizeError e) {
        log.writeLog("Authorize error: " + e.getMessage());
        return ResponseEntity.badRequest().body(new AuthorizeError("Authorize error: wrong login or password"));
    }

    @ExceptionHandler(FileError.class)
    public ResponseEntity handleErrorFile(FileError e) {
        log.writeLog("File error: " + e.getMessage());
        return ResponseEntity.internalServerError().body(new FileError("File error "));
    }

    @ExceptionHandler(TokenError.class)
    public ResponseEntity handleErrorToken(TokenError e) {
        log.writeLog("Token error" + e.getMessage());
        return ResponseEntity.status(400).body(new TokenError("Token error "));
    }
}
