package springboot.profpilot.model.MainPage.gameover;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Gameroom.GameRoom;

@Service
@RequiredArgsConstructor
public class GameOverService {
    private final GameRoom gameRoom;

    public GameOverService(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
    public void resetGame() {
        resetGameState();
        resetScore();
    }

    private void resetScore() {
        gameRoom.setScore(0);
    }

    private void resetGameState() {
        gameRoom.resetState();
    }
}