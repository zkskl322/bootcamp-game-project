package springboot.profpilot.model.MainPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Gamer.GamerRepository;
import springboot.profpilot.model.Gameroom.GameRoom;
import springboot.profpilot.model.Gameroom.GameRoomDTO;
import springboot.profpilot.model.Gameroom.GameRoomRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {
    private final GameRoomRepository gameRoomRepository;
    public MainPageDTO getMainPageData() {
        List<GameRoom> gameRooms = gameRoomRepository.findAll();
        List<GameRoomDTO> gameRoomDTOS = gameRooms.stream()
                .map(gameRoom -> new GameRoomDTO(
                        gameRoom.getId(),
                        gameRoom.getRoom_password(),
                        gameRoom.getGamers().get(0).getNickname(),
                        gameRoom.getRoomName(),
                        gameRoom.getRoom_size(),
                        gameRoom.getRoom_goal()
                )).collect(Collectors.toList());

        return new MainPageDTO(gameRoomDTOS);
    }
}
