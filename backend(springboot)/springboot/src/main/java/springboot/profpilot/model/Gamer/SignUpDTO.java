package springboot.profpilot.model.Gamer;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpDTO {
    private String nickname;
    private String password;
    private String name;
    private String email;
}
