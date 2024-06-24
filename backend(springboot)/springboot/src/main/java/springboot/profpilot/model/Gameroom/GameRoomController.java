package springboot.profpilot.model.Gameroom;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.web.bind.annotation.*;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.Gamer.GamerService;
import springboot.profpilot.model.logSystem.GameResult;
import springboot.profpilot.model.logSystem.GameResultService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;
    private final GamerService gamerService;
    private final GameRoomRepository gameRoomRepository;
    private final GameResultService gameResultService;

    @PostMapping("/game/room/create")
    public Long createRoom(@RequestBody GameRoomDTO gameRoomDTO, Principal principal) {
        GameRoom gameRoom = new GameRoom();
        GameResult gameResult = new GameResult();

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


        gameResult.setGameName(gameRoomDTO.getRoom_name());
        gameResult.setPlayer1Name(new_gamer.getNickname());
        gameResult.setPlayer2Name("None");
        gameResult.setGameDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        gameResult.setGameStatus("Waiting");

        GameRoom gameRoom1 = gameRoomRepository.findByRoomName(gameRoomDTO.getRoom_name());
        gameResult.setGameId(gameRoom1.getId());

        gameResultService.save(gameResult);
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
        GameResult gameResult = gameResultService.findByGameId(gameRoomId.getId());
        Gamer new_gamer = gamerService.findByNickname(principal.getName());
        Map<String, Object> response = new HashMap<>();
        List<Gamer> gamers = gameRoom.getGamers();

        response.put("response", "");
        response.put("room_name", gameRoom.getRoomName());
        response.put("room_goal", gameRoom.getRoom_goal());
        response.put("room_size", gameRoom.getRoom_size());
        response.put("room_password", gameRoom.getRoom_password());
        response.put("owner_nickname", gameRoom.getOwnerNickname());
        response.put("Owner", gameRoom.getOwnerNickname());

        if (gamers.size() == 1) {
            if (gameRoom.getOwnerNickname().equals(new_gamer.getNickname())) {
                response.put("Gamer1", gameRoom.getOwnerNickname());
                response.put("Gamer2", "waiting...");
                response.put("response", "You already in room1");
            } else {
                response.put("Gamer1", gameRoom.getOwnerNickname());
                response.put("Gamer2", new_gamer.getNickname());
                response.put("response", "Join GameRoom Success");
            }
        }
        if (gamers.size() == 2) {
            response.put("Gamer1", gameRoom.getOwnerNickname());
            if (gamers.get(0).getNickname().equals(gameRoom.getOwnerNickname())) {
                response.put("Gamer2", gamers.get(1).getNickname());
            } else {
                response.put("Gamer2", gamers.get(0).getNickname());
            }
        }

        if (gameRoom.getOwnerNickname().equals(new_gamer.getNickname())) {
            response.put("response", "You already in room1");
            return response;
        } else if (gamers.get(0).getNickname().equals(new_gamer.getNickname())) {
            response.put("response", "You already in room2");
            return response;
        }

        else if (gamers.size() == 2) {
            if (gamers.get(1).getNickname().equals(new_gamer.getNickname())) {
                response.put("response", "You already in room2");
            }
            if (!gameRoom.getGamers().get(0).getNickname().equals(new_gamer.getNickname() ) && !gameRoom.getGamers().get(1).getNickname().equals(new_gamer.getNickname())) {
                response.put("response", "Room is full");
                return response;
            }
        }

        if (gameRoom.getGamers().size() == 1) {

            gameResult.setPlayer2Name(new_gamer.getNickname());
            gameResult.setGameStatus("Ready");
            gameResultService.save(gameResult);

            gamers.add(new_gamer);
            gameRoom.setGamers(gamers);
            gameRoomRepository.save(gameRoom);
            System.out.println("Join GameRoom Success");
            System.out.println("response : " + response.get("response"));
            return response;
        }
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




























