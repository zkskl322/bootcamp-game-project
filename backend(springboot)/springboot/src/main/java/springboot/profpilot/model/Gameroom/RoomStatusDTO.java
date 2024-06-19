package springboot.profpilot.model.Gameroom;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RoomStatusDTO {
    private GameRoomDTO gameRoom;
    private List<ReadyStateDTO> players;

    public RoomStatusDTO(GameRoomDTO gameRoom, List<ReadyStateDTO> players) {
        this.gameRoom = gameRoom;
        this.players = players;
    }
}
