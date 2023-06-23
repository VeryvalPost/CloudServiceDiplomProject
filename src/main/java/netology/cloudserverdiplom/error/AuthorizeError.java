package netology.cloudserverdiplom.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AuthorizeError extends RuntimeException{
    public AuthorizeError(String msg) {
        super(msg);
    }
}
