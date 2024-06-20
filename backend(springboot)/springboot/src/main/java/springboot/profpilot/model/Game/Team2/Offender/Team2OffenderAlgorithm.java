package springboot.profpilot.model.Game.Team2.Offender;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.GameState;

@Service
public class Team2OffenderAlgorithm {
    public GameState updateOnPossession(GameState gameState) {
        Team2OffenderGameCondition defenderGameCondition = new Team2OffenderGameCondition(gameState);
        defenderGameCondition.updateDefender3Position();
        return gameState;
    }
}