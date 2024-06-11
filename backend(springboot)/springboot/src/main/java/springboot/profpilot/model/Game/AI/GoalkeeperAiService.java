package springboot.profpilot.model.Game.AI;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.GamePlayer;
import springboot.profpilot.model.Game.GameState;

@Service
public class GoalkeeperAiService {

    public GameState update(GameState gameState) {
        double ball_y = gameState.getBall_y();
        GamePlayer goalkeeper_1 = gameState.getPlayer1_players().getPlayers().get(4);
        GamePlayer goalkeeper_2 = gameState.getPlayer2_players().getPlayers().get(4);
        if (ball_y > goalkeeper_1.getPlayer_y() + 0.1) {
            if (goalkeeper_1.getPlayer_y() < 4) {
                goalkeeper_1.setPlayer_y(goalkeeper_1.getPlayer_y() + 0.01);
            }
        }
        else if (ball_y < goalkeeper_1.getPlayer_y() - 0.1) {
            if (goalkeeper_1.getPlayer_y() > 3) {
                goalkeeper_1.setPlayer_y(goalkeeper_1.getPlayer_y() - 0.01);
            }
        }
        if (ball_y > goalkeeper_2.getPlayer_y() + 0.1) {
            if (goalkeeper_2.getPlayer_y() < 4) {
                goalkeeper_2.setPlayer_y(goalkeeper_2.getPlayer_y() + 0.01);
            }
        }
        else if (ball_y < goalkeeper_2.getPlayer_y() - 0.1) {
            if (goalkeeper_2.getPlayer_y() > 3) {
                goalkeeper_2.setPlayer_y(goalkeeper_2.getPlayer_y() - 0.01);
            }
        }

        if (gameState.getIsFirstHalf() == 1) {
            if (goalkeeper_1.getPlayer_x() > 0.1) {
                goalkeeper_1.setPlayer_x(goalkeeper_1.getPlayer_x() - 0.01);
            }
            if (goalkeeper_2.getPlayer_x() < 10.9) {
                goalkeeper_2.setPlayer_x(goalkeeper_2.getPlayer_x() + 0.01);
            }
        }
        else {
            if (goalkeeper_1.getPlayer_x() < 10.9) {
                goalkeeper_1.setPlayer_x(goalkeeper_1.getPlayer_x() + 0.01);
            }
            if (goalkeeper_2.getPlayer_x() > 0.1) {
                goalkeeper_2.setPlayer_x(goalkeeper_2.getPlayer_x() - 0.01);
            }
        }

        return gameState;
    }
}
