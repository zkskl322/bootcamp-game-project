package springboot.profpilot.model.Gameroom;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class GameRoomDTO {

    private String room_password;
    private String room_name;
    private Long room_size;
    private Long room_goal;

    public GameRoomDTO(String room_password, String room_name, Long room_size, Long room_goal) {
        this.room_password = room_password;
        this.room_name = room_name;
        this.room_size = room_size;
        this.room_goal = room_goal;
    }
}
