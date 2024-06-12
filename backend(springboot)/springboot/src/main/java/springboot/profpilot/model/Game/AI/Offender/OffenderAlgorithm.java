package springboot.profpilot.model.Game.AI.Offender;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.Core.*;
import springboot.profpilot.model.Game.AI.Core.Action.Action;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;

@Service
public class OffenderAlgorithm {

    public GameState update(GameState gameState) {
        OffenderGameConditions conditions = new OffenderGameConditions(gameState);
        OffenderGameActions actions = new OffenderGameActions(gameState);

        // team1 공격수 행동트리
        // select와 squence를 이용하여 행동트리를 구성 -> false가 나오면 종료.
        // 트리에서 이어나가고 싶으면 true를 반환해야 함.
        AiNode Offender_behaviorTree1 = new Selector(Arrays.asList(
                new Condition(() -> conditions.isTeamWithBall(1)),
                new Sequence(Arrays.asList(
                        new Condition(() -> conditions.isOtherOffenderControlBall(1)),
                        new Action(() -> actions.SameTeamOffenderMoveBall(gameState.getPlayer1_control_player(), 1))
                ))
        ));

        // team2 공격수 행동트리
        AiNode Offender_behaviorTree2 = new Selector(Arrays.asList(
                new Condition(() -> conditions.isTeamWithBall(2)),
                new Sequence(Arrays.asList(
                        new Condition(() -> conditions.isOtherOffenderControlBall(2)),
                        new Action(() -> actions.SameTeamOffenderMoveBall(gameState.getPlayer1_control_player(), 2))
                ))
        ));


        Offender_behaviorTree1.run();
        Offender_behaviorTree2.run();
        return gameState;

    }

}