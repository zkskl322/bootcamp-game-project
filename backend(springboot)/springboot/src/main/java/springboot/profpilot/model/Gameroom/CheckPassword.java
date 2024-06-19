package springboot.profpilot.model.Gameroom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckPassword {
    private Long roomId;
    private String password;
}
