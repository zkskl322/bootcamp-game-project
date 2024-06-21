package springboot.profpilot.model.Game.Team1.Defend;

import springboot.profpilot.model.Game.AI.Core.Action.Action;
import springboot.profpilot.model.Game.AI.Core.AiNode;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.AI.Core.Selector;
import springboot.profpilot.model.Game.AI.Core.Sequence;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;

public class Team1DefenderAlgorithm {
    public void update(GameState gameState) {
        Team1DefenderGameAction defenderGameAction = new Team1DefenderGameAction(gameState);
        Team1DefenderGameCondition defenderGameCondition = new Team1DefenderGameCondition(gameState);

        // 수비 행동트리

        // team1 공격수0 행동트리
        // 1. 상대 팀이 공을 가지면, 일단 우리 팀 골대 앞 측정 지정한 위치에 간다.
        // 2. 해당 지역 위치에 상대팀 선수가 들어오지 않아야 한다.
        AiNode Offender0_behaviorTree0 = new Selector(Arrays.asList(
            new Condition(() -> defenderGameCondition.isTeamWithBall(2)), // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
//                new Condition(defenderGameCondition::isOpponentInPositionOffender0), // 상대팀 선수가 해당 위치에 있는 경우
//                new Action(defenderGameAction::moveToPositionOffender0) // 해당 위치로 이동
            ))
        ));

        // 1. 상대팀이 공을 가지고, 해당 위치 안에 존재하면 해당 플레이어를 따라간다.
        AiNode Offender0_behaviorTree1 = new Selector(Arrays.asList(
//            new Condition(() -> defenderGameCondition.isTeamWithBall(2)) // 2 팀이 공을 가지고 있는 경우
            new Sequence(Arrays.asList(
//                new Condition(!defenderGameCondition::isOpponentInPositionOffender0), // 상대팀 선수가 해당 위치에 있는 경우
//                new Action(defenderGameAction::followOpponentOffender0) // 해당 위치로 이동
            ))
        ));

        Offender0_behaviorTree0.run();
        Offender0_behaviorTree1.run();

    }
}
