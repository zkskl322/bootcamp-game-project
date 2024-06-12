package springboot.profpilot.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String nickname;
    private String password;
    private String name;
    private String email;

    public UserDTO(String nickname, String password, String name, String email) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
