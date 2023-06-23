package netology.cloudserverdiplom.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AuthorizeData {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
