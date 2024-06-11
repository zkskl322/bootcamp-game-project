package springboot.profpilot.model.Gameroom;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRoomService {
    private final GameRoomRepository gameRoomRepository;
    private final PasswordEncoder passwordEncoder;

    public GameRoom save(GameRoom gameRoom) {
        // 방장 기반으로 해서 -> 중복된 방 생성되지 않게 처리

        gameRoom.setRoom_name(gameRoom.getRoom_name());
        gameRoom.setRoom_password(passwordEncoder.encode(gameRoom.getRoom_password()));
        gameRoom.setRoom_size(gameRoom.getRoom_size());
        gameRoom.setRoom_goal(gameRoom.getRoom_goal());
        return gameRoomRepository.save(gameRoom);
    }

    public void delete(Long id) {
        gameRoomRepository.deleteById(id);
    }
}
