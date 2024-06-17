package springboot.profpilot.model.Gameroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.profpilot.model.MainPage.MainPageDTO;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;

    @PostMapping("/game/room/create")
    @ResponseBody
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
    @ResponseBody
    public String deleteRoom(@PathVariable("id") Long id) {
        gameRoomService.delete(id);
        return "delete GameRoom";
    }

    @GetMapping("/game/room/{id}")
    @ResponseBody
    public RoomStatusDTO getRoomStatus(@PathVariable("id") Long id) {
        GameRoomDTO gameRoom = gameRoomService.getGameRoom(id);
        List<ReadyStateDTO> players = gameRoomService.getPlayersStatus(id);
        return new RoomStatusDTO(gameRoom, players);
    }
}