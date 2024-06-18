package springboot.profpilot.model.MainPage;

import lombok.Getter;
import lombok.Setter;
import springboot.profpilot.model.Gameroom.GameRoomDTO;
import java.util.List;

@Getter
@Setter
public class MainPageDTO {
    private List<GameRoomDTO> gameRooms;
//    private List<UserDTO> userProfile;

    public MainPageDTO(List<GameRoomDTO> gameRooms) {
        this.gameRooms = gameRooms;
//        this.userProfile = userProfile;
    }


}
