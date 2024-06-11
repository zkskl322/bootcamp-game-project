package springboot.profpilot.model.DTO.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {
    private String password;
    private String newPassword;
}
