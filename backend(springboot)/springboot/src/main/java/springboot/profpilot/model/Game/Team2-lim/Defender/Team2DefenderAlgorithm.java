package springboot.profpilot.model.Game.Team2.Defender;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.Core.Action.Action;
import springboot.profpilot.model.Game.AI.Core.AiNode;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.AI.Core.Selector;
import springboot.profpilot.model.Game.AI.Core.Sequence;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;
@Service
public class Team2DefenderAlgorithm {
    public GameState updateOnPossession(GameState gameState) {
        Team2DefenderGameActions actions = new Team2DefenderGameActions(gameState);
        Team2DefenderGameConditions conditions = new Team2DefenderGameConditions(gameState);
        AiNode Defender3_behaviorTree1 = new Selector(Arrays.asList(
                new Condition(() -> conditions.isTeamWithBall(1)),
                new Sequence(Arrays.asList(
                        new Condition(conditions::isOffender1HasBall),
                        new Condition(conditions::isOffender1InDefenseZone),
                        new Action(() -> actions.mirrorFollowOffender())
                ))
        ));
        Defender3_behaviorTree1.run();
        return gameState;
    }
}