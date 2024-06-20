package springboot.profpilot.model.Game.Team2.Defender;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.Core.Action.Action;
import springboot.profpilot.model.Game.AI.Core.AiNode;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.AI.Core.Sequence;
import springboot.profpilot.model.Game.AI.Core.Selector;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;

@Service
public class Team2DefenderAlgorithm {

    public GameState updateOnPossession(GameState gameState) {
        Team2DefenderGameConditions conditions = new Team2DefenderGameConditions(gameState);
        Team2DefenderGameActions actions = new Team2DefenderGameActions(gameState);

        AiNode Offender0_behaviorTree = new Selector(Arrays.asList(
                new Condition(() -> !conditions.isTeamWithBall(2)), // 팀2가 공을 가지고 있지 않은 경우
                new Condition(conditions::isOpponentCrossedMidLine), // 팀1의 공격수가 중앙선을 넘어온 경우
                new Action(actions::followOpponentOffender) // 0번 공격수가 팀1의 공격수를 따라다닙니다.
        ));
        Offender0_behaviorTree.run();
        return gameState;
    }
}