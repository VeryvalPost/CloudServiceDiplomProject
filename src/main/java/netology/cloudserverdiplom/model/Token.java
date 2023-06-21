package netology.cloudserverdiplom.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    @JsonProperty("auth-token")
    private String token;
}
