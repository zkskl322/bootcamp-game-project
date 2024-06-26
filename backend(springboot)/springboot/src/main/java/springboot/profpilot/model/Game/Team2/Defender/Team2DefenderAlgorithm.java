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

        AiNode Offender0_behaviorTree = new Sequence(Arrays.asList(
                new Condition(() -> !conditions.isTeamWithBall(2)), // 팀2가 공을 가지고 있지 않은 경우
                new Condition(conditions::isOpponentCrossedMidLine), // 팀1의 공격수가 중앙선을 넘어온 경우
                new Condition(() -> !conditions.isPlayerNearOpponent(0, 0.5)), // 0번 선수가 팀1의 공격수와 0.5 이내에 있지 않은 경우
                new Action(actions::followOpponentOffender) // 0번 공격수가 팀1의 공격수를 따라다닙니다.
        ));

        AiNode Defender2_behaviorTree = new Sequence(Arrays.asList(
                new Condition(() -> conditions.isTeamWithBall(1)), // 팀2가 공을 가지고 있지 않은 경우
                new Action(() -> actions.movePlayer2ToY(3.5)) // 2번 선수가 Y축으로 3.5 움직입니다.
        ));

        AiNode Defender3_behaviorTree1 = new Selector(Arrays.asList(
            new Condition(() -> conditions.isTeamWithBall(1)),
            new Sequence(Arrays.asList(
                    new Condition(conditions::isOffender1HasBall), // 상대방 공격수 1번이 공을 소유
                    new Condition(conditions::isOffender1InDefenseZone), // 경기장 기준(우리팀 수비진 있는 공간)까지 들어옴
                    new Action(() -> actions.mirrorFollowOffender()) // 우리팀 수비수가 상대팀 공격수 1번을 따라온다.
                ))
        ));
        
        Defender2_behaviorTree.run();
        Defender3_behaviorTree1.run();
        Offender0_behaviorTree.run();
        return gameState;
    }
}