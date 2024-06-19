package springboot.profpilot.model.Gamer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GamerLobbyDTO {
    private String realname;

    public GamerLobbyDTO(String realname) {
        this.realname = realname;
    }
}
