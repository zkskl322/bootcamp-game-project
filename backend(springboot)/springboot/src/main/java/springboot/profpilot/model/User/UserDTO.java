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
    private Long win_score;
    private Long lose_score;

    public UserDTO(String nickname, String password, String name, String email, Long win_score, Long lose_score) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.email = email;
        this.win_score = win_score;
        this.lose_score = lose_score;
    }
}
