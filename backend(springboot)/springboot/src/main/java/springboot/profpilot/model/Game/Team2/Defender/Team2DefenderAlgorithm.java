package springboot.profpilot.model.Game.Team2.Defender;
import springboot.profpilot.model.Game.AI.Core.Action.Action;
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
                        new Condition(conditions::isOffenderHasBall), // 상대방 공격수 0, 1 번이 공을 가지고 있는지 확인
                        new Condition(conditions::isOffenderInDefenseZone), // 상대방 공격수가 공격진 공간에 들어왔는지 확인
                        new Action(() -> {
                            actions.mirrorFollowOffender(3, 1); // 수비수 2(3)이 상대방 공격수 1(1)을 대칭하면서 따라감
                        })
                ))
        ));
        Defender3_behaviorTree1.run();

    }
}