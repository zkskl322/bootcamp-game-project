package springboot.profpilot.model.Gameroom;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.Gamer.GamerService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;
    private final GamerService gamerService;
    private final GameRoomRepository gameRoomRepository;

    @PostMapping("/game/room/create")
    public Long createRoom(@RequestBody GameRoomDTO gameRoomDTO, Principal principal) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setRoom_password(gameRoomDTO.getRoom_password());
        gameRoom.setRoomName(gameRoomDTO.getRoom_name());
        gameRoom.setRoom_goal(gameRoomDTO.getRoom_goal());
        gameRoom.setRoom_size(gameRoomDTO.getRoom_size());

        Gamer new_gamer = gamerService.findByNickname(principal.getName());
        List<Gamer> gamers = gameRoom.getGamers();
        gamers.add(new_gamer);
        gameRoom.setGamers(gamers);
        gameRoomRepository.save(gameRoom);


        GameRoom gameRoom1 = gameRoomRepository.findByRoomName(gameRoomDTO.getRoom_name());
        return gameRoom1.getId();
    }

    @GetMapping("/game/room/delete/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {
        gameRoomService.delete(id);
        return "delete GameRoom";
    }


    @PostMapping("/game/room/join")
    public String JoinRoom(@RequestBody GameRoomIdDTO gameRoomId, Principal principal) {
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId.getId()).orElseThrow(() -> new RuntimeException("GameRoom not found"));
        List<Gamer> gamers = gameRoom.getGamers();
        if (gamers.get(0).getNickname().equals(principal.getName())) {
            return "You already in room";
        }
        if (gameRoom.getGamers().size() == 1) {
            Gamer new_gamer = gamerService.findByNickname(principal.getName());
            gamers.add(new_gamer);
            gameRoom.setGamers(gamers);
            gameRoomRepository.save(gameRoom);
            return "Join GameRoom";
        }
        return "You can't join room";
    }

}