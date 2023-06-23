package netology.cloudserverdiplom.model;


import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

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
