package springboot.profpilot.model.Game.Team2.Offender;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.Core.AiNode;
import springboot.profpilot.model.Game.GameState;

@Service
public class Team2OffenderAlgorithm {
    public GameState updateOnPossession(GameState gameState) {
        Team2OffenderGameConditions condition = new Team2OffenderGameConditions(gameState);
        Team2OffenderGameActions actions = new Team2OffenderGameActions(gameState);

        return gameState;
    }
}