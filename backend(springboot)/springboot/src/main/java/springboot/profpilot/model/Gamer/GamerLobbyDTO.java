package springboot.profpilot.model.Gamer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GamerLobbyDTO {
    private Long id;
    private String realname;

    public GamerLobbyDTO(String realname, Long id) {
        this.id = id;
        this.realname = realname;
    }
}
