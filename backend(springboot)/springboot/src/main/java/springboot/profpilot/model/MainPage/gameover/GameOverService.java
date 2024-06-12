package springboot.profpilot.model.MainPage.gameover;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Gameroom.GameRoom;

@Service
public class GameOverService {
    private final GameRoom gameRoom;

    @Autowired
    public GameOverService(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }


    public void resetGame() {
        resetGameState(gameRoom);
        resetScore(gameRoom);
    }

    private void resetScore(GameRoom gameRoom) {
        gameRoom.setScore(0);
    }

    private void resetGameState(GameRoom gameRoom) {
        gameRoom.resetState();
    }
}