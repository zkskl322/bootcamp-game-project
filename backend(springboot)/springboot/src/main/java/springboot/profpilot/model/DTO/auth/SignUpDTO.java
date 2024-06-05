package springboot.profpilot.model.DTO.auth;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Long studentId;
}
