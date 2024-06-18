package springboot.profpilot.model.Gameroom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRoomOwnerDTO {
    private String room_GameOwnerNickname;

    public GameRoomOwnerDTO(String room_GameOwnerNickname) {
        this.room_GameOwnerNickname = room_GameOwnerNickname;
    }
}
