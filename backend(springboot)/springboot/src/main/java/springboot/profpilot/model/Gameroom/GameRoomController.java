package springboot.profpilot.model.Gameroom;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.web.bind.annotation.*;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.Gamer.GamerService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        gameRoom.setOwnerNickname(new_gamer.getNickname());
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
    public Map<String, Object> JoinRoom(@RequestBody GameRoomIdDTO gameRoomId, Principal principal) {
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId.getId()).orElseThrow(() -> new RuntimeException("GameRoom not found"));
        List<Gamer> gamers = gameRoom.getGamers();
        Map<String, Object> response = new HashMap<>();

        response.put("response", "");
        response.put("room_name", gameRoom.getRoomName());
        response.put("room_goal", gameRoom.getRoom_goal());
        response.put("room_size", gameRoom.getRoom_size());
        response.put("room_password", gameRoom.getRoom_password());
        response.put("owner_nickname", gameRoom.getOwnerNickname());
        response.put("Owner", gameRoom.getOwnerNickname());
        if (gamers.size() == 1) {
            response.put("Gamer1", gamers.get(0).getNickname());
            response.put("Gamer2", "waiting...");
        }
        if (gamers.size() == 2) {
            response.put("Gamer1", gamers.get(0).getNickname());
            response.put("Gamer2", gamers.get(1).getNickname());
        }

        if (gameRoom.getOwnerNickname().equals(principal.getName())) {
            response.put("response", "You already in room1");
            return response;
        } else if (gamers.get(0).getNickname().equals(principal.getName())) {
            response.put("response", "You already in room2");
            return response;
        } else if (gamers.size() == 2) {
            if (gamers.get(1).getNickname().equals(principal.getName())) {
                response.put("response", "You already in room2");
                return response;
            } else {
                response.put("response", "Room is full");
                return response;
            }
        }

        if (gameRoom.getGamers().size() == 1) {
            Gamer new_gamer = gamerService.findByNickname(principal.getName());
            gamers.add(new_gamer);
            gameRoom.setGamers(gamers);
            gameRoomRepository.save(gameRoom);
            response.put("response", "Join GameRoom");
            return response;
        }
        response.put("response", "Room is full");
        return response;
    }

    @PostMapping("/game/room/delete")
    public String leaveOrDeleteRoom(@RequestBody GameRoomIdDTO gameRoomId, Principal principal) {
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId.getId()).orElseThrow(() -> new RuntimeException("GameRoom not found"));
        List<Gamer> gamers = gameRoom.getGamers();

        if (gameRoom.getOwnerNickname().equals(principal.getName())) {
            gameRoomService.delete(gameRoomId.getId());
            return "Delete GameRoom";
        } if (gamers.get(0).getNickname().equals(principal.getName())) {
            gamers.remove(0);
            gameRoom.setGamers(gamers);
            gameRoomRepository.save(gameRoom);
            return "Leave GameRoom";
        } else if (gamers.get(1).getNickname().equals(principal.getName())) {
            gamers.remove(1);
            gameRoom.setGamers(gamers);
            gameRoomRepository.save(gameRoom);
            return "Leave GameRoom";
        }
        return "You can't leave room";
    }

    @PostMapping("/game/room/checkPassword")
    public Map<String, Object> checkPassword(@RequestBody CheckPassword checkPassword) {
        GameRoom gameRoom = gameRoomRepository.findById(checkPassword.getRoomId()).orElseThrow(() -> new RuntimeException("GameRoom not found"));
        Map<String, Object> response = new HashMap<>();

        if (gameRoom.getRoom_password().equals(checkPassword.getPassword())) {
            response.put("result", "success");
            return response;
        }
        response.put("result", "fail");
        return response;
    }

}




























