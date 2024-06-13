package springboot.profpilot.model.MainPage.gameover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Gameroom.GameRoom;
import springboot.profpilot.model.Gameroom.GameRoomRepository;

@Service
public class GameOverService {
    private final GameRoom gameRoom;
    private final GameRoomRepository gameRoomRepository;

    @Autowired
    public GameOverService(GameRoom gameRoom, GameRoomRepository gameRoomRepository) {
        this.gameRoom = gameRoom;
        this.gameRoomRepository = gameRoomRepository;
    }

    public void resetGame() {
        resetGameState(gameRoom);
        resetScore(gameRoom);
    }

    public void saveGameState(GameRoom gameRoom) {
        gameRoomRepository.save(gameRoom);
    }

    public void resetAndSaveGame() {
        resetGame();
        saveGameState(gameRoom);
    }

    private void resetScore(GameRoom gameRoom) {
        gameRoom.setScore(0);
    }

    private void resetGameState(GameRoom gameRoom) {
        gameRoom.resetState();
    }
}