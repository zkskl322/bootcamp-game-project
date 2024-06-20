package springboot.profpilot.model.Gamer;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpDTO {
    private String email;
    private String realname;
    private String nickname;
    private String password;
}
