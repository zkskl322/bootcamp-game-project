package springboot.profpilot.model.Gameroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRoomService {
    private final GameRoomRepository gameRoomRepository;

    public GameRoom save(GameRoom gameRoom) {
        gameRoom.setRoom_name(gameRoom.getRoom_name());
        gameRoom.setRoom_password(gameRoom.getRoom_password());
        gameRoom.setRoom_size(gameRoom.getRoom_size());
        gameRoom.setRoom_goal(gameRoom.getRoom_goal());
        return gameRoomRepository.save(gameRoom);
    }

    public void delete(Long id) {
        gameRoomRepository.deleteById(id);
    }
}
