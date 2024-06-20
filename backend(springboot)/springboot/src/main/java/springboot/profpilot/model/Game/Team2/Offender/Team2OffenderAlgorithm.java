package springboot.profpilot.model.Game.Team2.Offender;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.Core.Action.Action;
import springboot.profpilot.model.Game.AI.Core.AiNode;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.AI.Core.Selector;
import springboot.profpilot.model.Game.AI.Core.Sequence;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;

@Service
public class Team2OffenderAlgorithm {
    public GameState updateOnPossession(GameState gameState) {
        Team2OffenderGameConditions conditions = new Team2OffenderGameConditions(gameState);
        Team2OffenderGameActions actions = new Team2OffenderGameActions(gameState);

        AiNode Offender0_behaviorTree1 = new Selector(Arrays.asList(
                new Condition(() -> conditions.isTeamWithBall(2)),
                new Sequence(Arrays.asList(
                        new Condition(conditions::isTeam2Offender0WithBall), // 조건: 우리팀 공격수 0번이 공을 가지고 있다
                        new Condition(conditions::isTeam2Offender0RunningToTeam1), // 조건: 우리팀 공격수 0번이 Team1쪽으로 뛴다
                        new Action(actions::moveOffender1ToDifferentYSameX) // 행동: 우리팀 공격수 1번이 0번과 다른 y값의 같은 x값으로 뛴다
                ))
        ));
        Offender0_behaviorTree1.run();
        return gameState;
    }
}