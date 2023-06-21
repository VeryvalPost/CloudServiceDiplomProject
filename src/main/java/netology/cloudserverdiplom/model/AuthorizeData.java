package netology.cloudserverdiplom.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeData {
    private String login;
    private String password;
}
