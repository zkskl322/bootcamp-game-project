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
        AiNode Offender0_behaviorTree1 = new Selector(Arrays.asList(
                // 1. 0번 공격수 공 소유시 알고리즘 (정은성)
                // 1-1 : 조건 1. 0번이 공을 가지고 있지 않지만, 1 팀이 공을 가지고 있는 경우 -> 2번과 3번 사이로 이동
                new Condition(() -> conditions.isTeamWithBall(1)), // 1 팀이 공을 가지고 있는 경우
                new Sequence(Arrays.asList(
                        // 1-2. 1 팀이 공을 소유하고 있고, 현재 0번 공격수가 2번과 3번 사이에 있는데, 공과 자신 사이에 상대팀이 있는 경우 -> 상대팀과 겹치지 않도록 이동
                        new Sequence(Arrays.asList(
                                new Condition(conditions::isOffender0NotPossessBall), // 0번이 아닌 선수가 공을 가지고 있는 경우
                                new Condition(conditions::isOffender0InBetweenDefender), // 0번이 2번과 3번 사이에 있는 경우
                                new Condition(() -> conditions.isOffender0CenterGoOkay(0)), // 중앙이 유망하지 않을 때 true 반환 -> 상대팀과 겹치지 않도록 이동
                                new Action(actions::MoveOffender0BetweenAvailablePositionDefender) // 0번 공격수 이동 -> 상대팀과 겹치지 않도록 이동
                        ))
                ))


        ));

        // 1-1. 중앙에 위치하는 것이 공과 나 사이에 상대팀이 없음. -> 2번과 3번 사이로 이동
        AiNode Offender0_behaviorTree2 = new Selector(Arrays.asList(
                new Condition(() -> conditions.isTeamWithBall(1)), // 1 팀이 공을 가지고 있는 경우
                new Selector(Arrays.asList(
                        new Condition(conditions::isOffender0NotPossessBall), // 0번이 공을 가지고 있지 않은 경우
                        new Condition(() -> conditions.isOffender0CenterGoOkay(1)), // 중앙이 유망하지 않을 때 false 반환
                        new Action(actions::MoveBetweenDefender) // 2번과 3번 사이로 이동
                ))
        ));


        Offender0_behaviorTree1.run();
        Offender0_behaviorTree2.run();
        return gameState;

    }

}
