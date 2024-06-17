package springboot.profpilot.model.Gamer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamerLobbyDTO {
    private String realname;

    public GamerLobbyDTO(String realname) {
        this.realname = realname;
    }
}
