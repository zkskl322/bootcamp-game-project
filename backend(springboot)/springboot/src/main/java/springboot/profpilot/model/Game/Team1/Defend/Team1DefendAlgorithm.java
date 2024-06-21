package springboot.profpilot.model.Game.Team1.Defend;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.Core.Action.Action;
import springboot.profpilot.model.Game.AI.Core.AiNode;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.AI.Core.Selector;
import springboot.profpilot.model.Game.AI.Core.Sequence;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;
//----------------------
//|    3 1        1 3  |
//| 4        vs      4 |
//|    2 0        0 2  |
//----------------------

        // 수비 행동트리 : 지역 수비 기반 알고리즘
        // 전반전:
        // 0번은 x: 2~4, y: 3.5~6 / 중앙 (3, 4.75)
        // 1번은 x: 2~4, y: 1~3.5 / 중앙 (3, 2.25)
        // 2번은 x: 0~2, y: 3.5~6 / 중앙 (1, 4.75)
        // 3번은 x: 0~2, y: 1~3.5 / 중앙 (1, 2.25)

        // 후반전:
        // 0번은 x: 7~9, y: 3.5~6 / 중앙 (8, 4.75)
        // 1번은 x: 7~9, y: 1~3.5 / 중앙 (8, 2.25)
        // 2번은 x: 9~11, y: 3.5~6 / 중앙 (10, 4.75)
        // 3번은 x: 9~11, y: 1~3.5 / 중앙 (10, 2.25)

@Service
public class Team1DefendAlgorithm {
    public GameState update(GameState gameState) {
        Team1DefendGameAction action = new Team1DefendGameAction(gameState);
        Team1DefendGameCondition condition = new Team1DefendGameCondition(gameState);

        // team1 공격수0 행동트리 --------------------------------------------------- //
        // 1. 상대 팀이 공을 가지면, 개인 전담 지역 수비 지역으로 이동한다.
        // 2. 해당 지역 위치에 상대팀 선수가 들어오지 않으면, 해당 알고리즘을 진행
        AiNode Offender0_behaviorTree0 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
                new Condition(() -> condition.isOpponentInPositionOffender0(1)), // 상대팀 선수가 해당 위치에 있는 경우
                new Action(action::moveToPositionOffender0) // 해당 위치로 이동
            ))
        ));

        // 1. 상대팀이 공을 가지고, 내 개인 전담 지역에 상대팀 선수가 들어오면, 해당 선수를 따라간다.
        AiNode Offender0_behaviorTree1 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
                new Condition(() -> condition.isOpponentInPositionOffender0(2)), // 상대팀 선수가 해당 위치에 있는 경우
                new Action(action::followOpponentOffender0) // 해당 위치로 이동
            ))
        ));
        // team1 공격수0 행동트리 --------------------------------------------------- //



        // team1 공격수1 행동트리 --------------------------------------------------- //
        // 1. 상대 팀이 공을 가지면, 개인 전담 지역 수비 지역으로 이동한다.
        // 2. 해당 지역 위치에 상대팀 선수가 들어오지 않으면, 해당 알고리즘을 진행
        AiNode Offender1_behaviorTree0 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
                new Condition(() -> condition.isOpponentInPositionOffender1(1)), // 상대팀 선수가 해당 위치에 있는 경우
                new Action(action::moveToPositionOffender1) // 해당 위치로 이동
            ))
        ));
        // 1. 상대팀이 공을 가지고, 내 개인 전담 지역에 상대팀 선수가 들어오면, 해당 선수를 따라간다.
        AiNode Offender1_behaviorTree1 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
                new Condition(() -> condition.isOpponentInPositionOffender1(2)), // 상대팀 선수가 해당 위치에 있는 경우
                new Action(action::followOpponentOffender1) // 해당 위치로 이동
            ))
        ));
        // team1 공격수1 행동트리 --------------------------------------------------- //



        // team1 수비수2 행동트리 --------------------------------------------------- //
        // 1. 상대 팀이 공을 가지면, 개인 전담 지역 수비 지역으로 이동한다.
        // 2. 해당 지역 위치에 상대팀 선수가 들어오지 않으면, 해당 알고리즘을 진행
        AiNode Offender2_behaviorTree0 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
                new Condition(() -> condition.isOpponentInPositionDefender2(1)), // 상대팀 선수가 해당 위치에 있는 경우
                new Action(action::moveToPositionDefender2) // 해당 위치로 이동
            ))
        ));

        // 1. 상대팀이 공을 가지고, 내 개인 전담 지역에 상대팀 선수가 들어오면, 해당 선수를 따라간다.
        AiNode Offender2_behaviorTree1 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
                new Condition(() -> condition.isOpponentInPositionDefender2(2)), // 상대팀 선수가 해당 위치에 있는 경우
                new Action(action::followOpponentDefender2) // 해당 위치로 이동
            ))
        ));
         // team1 수비수2 행동트리 ---------------------------------------------------



        // team1 수비수3 행동트리 --------------------------------------------------- //
        // 1. 상대 팀이 공을 가지면, 개인 전담 지역 수비 지역으로 이동한다.
        // 2. 해당 지역 위치에 상대팀 선수가 들어오지 않으면, 해당 알고리즘을 진행
        AiNode Offender3_behaviorTree0 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
                new Condition(() -> condition.isOpponentInPositionDefender3(1)), // 상대팀 선수가 해당 위치에 있는 경우
                new Action(action::moveToPositionDefender3) // 해당 위치로 이동
            ))
        ));


        // 1. 상대팀이 공을 가지고, 내 개인 전담 지역에 상대팀 선수가 들어오면, 해당 선수를 따라간다.
        AiNode Offender3_behaviorTree1 = new Selector(Arrays.asList(
            new Condition(() -> condition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
                new Condition(() -> condition.isOpponentInPositionDefender3(2)), // 상대팀 선수가 해당 위치에 있는 경우
                new Action(action::followOpponentDefender3) // 해당 위치로 이동
            ))
        ));
        // team1 수비수3 행동트리 --------------------------------------------------- //




        Offender0_behaviorTree0.run();
        Offender0_behaviorTree1.run();
        Offender1_behaviorTree0.run();
        Offender1_behaviorTree1.run();
        Offender2_behaviorTree0.run();
        Offender2_behaviorTree1.run();
        Offender3_behaviorTree0.run();
        Offender3_behaviorTree1.run();
        return gameState;

    }
}
