package springboot.profpilot.model.Game.Team2.Offender;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.Core.Action.Action;
import springboot.profpilot.model.Game.AI.Core.AiNode;
import springboot.profpilot.model.Game.AI.Core.Selector;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.AI.Core.Sequence;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;

@Service
public class Team2OffenderAlgorithm {
    public GameState updateOnPossession(GameState gameState) {
        Team2OffenderGameCondition condition = new Team2OffenderGameCondition(gameState);
        Team2OffenderGameAction action = new Team2OffenderGameAction(gameState);

        AiNode Defender2_behaviorTree = new Sequence(Arrays.asList(
            // 조건1. team2가 공을 가지고 있을 경우
            new Condition(condition::team2hasAball),
            // 조건2. 수비수 2번이 공을 가지고 있지 않은 경우
            new Condition(condition::team2Defender2hasNotBall),
            // 액션1. 중앙선과 자기 골대 사이에 위치한다.
            new Action(action::team2DefenderGotoMid)
        ));
                
        AiNode Defender3_behaviorTree = new Sequence(Arrays.asList(
            // 조건1. team2가 공을 가지고 있을 경우
            new Condition(condition::team2hasAball),
            // 액션1. 3번 수비수가 중앙으로 이동한다.
            new Action(action::moveDefender3ToCenter)
        ));
            
        AiNode Offender1_behaviorTree1 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)),
            new Sequence(Arrays.asList(
                new Condition(condition::isTeam2Offender0WithBall), // 조건: 우리팀 공격수 0번이 공을 가지고 있다
                new Condition(condition::isTeam2Offender0RunningToTeam1), // 조건: 우리팀 공격수 0번이 Team1쪽으로 뛴다
                new Action(action::moveOffender1ToDifferentYSameX) // 행동: 우리팀 공격수 1번이 0번과 다른 y값의 같은 x값으로 뛴다
                ))
        ));
                
        AiNode Offender0_behaviorTree1 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)),
            new Sequence(Arrays.asList(
                new Condition(condition::isTeam2Defender3WithBall), // 조건: 우리팀 수비수 3번이 공을 가지고 있다
                new Action(action::moveOffender0ToDefender3Diagonal) // 행동: 우리팀 공격수 0번이 수비수 3번과 대각선으로 일정 거리 유지
            ))
        ));
                            
        Defender2_behaviorTree.run();
        Defender3_behaviorTree.run();
        Offender1_behaviorTree1.run();
        Offender0_behaviorTree1.run();

        return gameState;
    }








}