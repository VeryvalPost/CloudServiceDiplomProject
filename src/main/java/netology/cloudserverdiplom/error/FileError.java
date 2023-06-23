package netology.cloudserverdiplom.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FileError extends RuntimeException{
    public FileError(String msg) {
        super(msg);
    }
}
