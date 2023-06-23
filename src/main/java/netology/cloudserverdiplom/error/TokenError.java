package netology.cloudserverdiplom.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TokenError extends RuntimeException{
    public TokenError(String msg) {
        super(msg);
    }
}
