package springboot.profpilot.model.Gameroom;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;

    @PostMapping("/game/room/create")
    public String createRoom(@RequestBody GameRoomDTO gameRoomDTO, Principal principal) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setRoom_password(gameRoomDTO.getRoom_password());
        gameRoom.setRoom_name(gameRoomDTO.getRoom_name());
        gameRoom.setRoom_goal(gameRoomDTO.getRoom_goal());
        gameRoom.setRoom_size(gameRoomDTO.getRoom_size());
        gameRoomService.save(gameRoom);
        return "create GameRoom";
    }

    @GetMapping("/game/room/delete/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {
        gameRoomService.delete(id);
        return "delete GameRoom";
    }

}