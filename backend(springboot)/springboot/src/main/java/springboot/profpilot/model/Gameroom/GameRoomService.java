package springboot.profpilot.model.Gameroom;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.Gamer.GamerLobbyDTO;
import springboot.profpilot.model.Gamer.GamerRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameRoomService {
    private static final Logger logger = LoggerFactory.getLogger(GameRoomService.class);
    private final GameRoomRepository gameRoomRepository;
    private final GamerRepository gamerRepository;

    public GameRoom save(GameRoom gameRoom) {
        gameRoom.setRoomName(gameRoom.getRoomName());
        gameRoom.setRoom_password(gameRoom.getRoom_password());
        gameRoom.setRoom_size(gameRoom.getRoom_size());
        gameRoom.setRoom_goal(gameRoom.getRoom_goal());
        return gameRoomRepository.save(gameRoom);
    }

    public void delete(Long id) {
        gameRoomRepository.deleteById(id);
    }

    public GameRoom getGameRoom(Long id) {
        Optional<GameRoom> gameRoom = gameRoomRepository.findById(id);
        return gameRoom.orElse(null);

    }
//        GameRoom gameRoom = gameRoomRepository.findById(id).orElseThrow(() -> new RuntimeException("GameRoom not found"));
//        return new GameRoomDTO(gameRoom.getRoom_password(), gameRoom.getRoom_name(), gameRoom.getRoom_goal(), gameRoom.getRoom_size());
    }

//    public List<ReadyStateDTO> getPlayersStatus(Long roomId) {
//        List<Gamer> gamers = gamerRepository.findByGameRoomId(roomId);
//        return gamers.stream()
//                .map(gamer -> new ReadyStateDTO(
//                        new GamerLobbyDTO(gamer.getRealname()),
//                        gamer.getIsReady()))
//                .collect(Collectors.toList());
//    }
//    public List<ReadyStateDTO> getPlayersStatus(Long id) {
//        // 실제 데이터베이스 또는 다른 소스로부터 유저와 준비 상태를 가져오는 로직을 구현합니다.
//        List<ReadyStateDTO> players = new ArrayList<>();
//        players.add(new ReadyStateDTO(new GamerLobbyDTO("John Doe"), true));
//        players.add(new ReadyStateDTO(new GamerLobbyDTO("Jane Doe"), false));
//        return players;
//    }
//}
