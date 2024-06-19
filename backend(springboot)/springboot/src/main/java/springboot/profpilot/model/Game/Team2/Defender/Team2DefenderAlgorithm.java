package springboot.profpilot.model.Game.Team2.Defender;
import springboot.profpilot.model.Game.AI.Core.AiNode;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.AI.Core.Selector;
import springboot.profpilot.model.Game.AI.Core.Sequence;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;

public class Team2DefenderAlgorithm {
    public void update(GameState gameState) {
        Team2DefenderGameActions actions = new Team2DefenderGameActions(gameState);
        Team2DefenderGameConditions conditions = new Team2DefenderGameConditions(gameState);

        AiNode Defender3_behaviorTree1 = new Selector(Arrays.asList(
                new Condition(() -> conditions.isTeamWithBall(1)),
                new Sequence(Arrays.asList(
                        new Sequence(Arrays.asList(
//                                new Condition()
                        ))
                ))
        ));
    }
}
