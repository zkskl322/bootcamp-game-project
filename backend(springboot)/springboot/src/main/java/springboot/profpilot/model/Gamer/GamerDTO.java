package springboot.profpilot.model.Gamer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamerDTO {

    private String nickname;
    private String password;
    private String realname;
    private String email;


    public GamerDTO(String nickname, String password, String realname, String email) {
        this.nickname = nickname;
        this.password = password;
        this.realname = realname;
        this.email = email;
    }
}
