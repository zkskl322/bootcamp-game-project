package springboot.profpilot.model.Gameroom;

import lombok.Getter;
import lombok.Setter;
import springboot.profpilot.model.Gamer.GamerDTO;
import springboot.profpilot.model.Gamer.GamerLobbyDTO;

@Getter
@Setter
public class ReadyStateDTO {
    private GamerLobbyDTO gamer;
    private boolean isReady;

    public ReadyStateDTO(GamerLobbyDTO gamer, boolean isReady) {
        this.gamer = gamer;
        this.isReady = isReady;
    }
}
