package springboot.profpilot.model.Game.Team1.Offender;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.Core.*;
import springboot.profpilot.model.Game.AI.Core.Action.Action;
import springboot.profpilot.model.Game.AI.Core.Condition.Condition;
import springboot.profpilot.model.Game.GameState;

import java.util.Arrays;

@Service
public class Team1OffenderAlgorithm {

    public GameState updateOnPossession(GameState gameState) {
        Team1OffenderGameConditions conditions = new Team1OffenderGameConditions(gameState);
        Team1OffenderGameActions actions = new Team1OffenderGameActions(gameState);

        // team1 공격수0 행동트리
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


        // team1 공격수1 행동트리
        // 1. 상대팀 0번과 2번, 그리고 1번과 3번 사이의 거리 중 하나가 3보다 크면 아래 알고리즘
        // 1-1. 상대팀 0번과 2번, 그리고 1번과 3번 사이의 거리중에 더 거리가 차이가 많이나는 공간으로 이동
        AiNode Offender1_behaviorTree1 = new Selector(Arrays.asList(
                new Condition(() -> conditions.isTeamWithBall(1)), // 1 팀이 공을 가지고 있는 경우
                new Sequence(Arrays.asList(
                    new Condition(conditions::isOffender1NotPossessBall), // 1번이 공을 가지고 있지 않은 경우
                    new Condition(conditions::isOffdender1InBetweenOpposite), // 이미 1번이 0,2 혹은 1,3 번 중 거리가 더 먼 사이의 공간에 위치한 경우
                    new Action(actions::MoveOffender1BetweenAvailablePositionDefender) // 1번 공격수 이동 -> 상대팀과 겹치지 않도록 이동
                ))
        ));





        // 2. 상대팀 0번과 2번, 그리고 1번과 3번 사이의 거리가 둘 다 3보다 작으면 아래 알고리즘
        // 2-1. 아크 서클 정도 거리에서 위아래로 이동
//        AiNode Offender1_behaviorTree2 = new Selector(Arrays.asList(
//                new Condition(() -> conditions.isTeamWithBall(1)), // 1 팀이 공을 가지고 있는 경우
//                new Sequence(Arrays.asList(
//                        new Condition(conditions::isOffender1NotPossessBall), // 1번이 공을 가지고 있지 않은 경우
//                        new Condition(conditions::isOtherTeamDistanceOkay(0)), // 상대팀 0,2 혹은 1,3 사이의 거리가 3보다 작은 경우
//                        new Action(actions::MoveOffender1BetweenArcCircle) // 1번 공격수 이동 -> 상대팀과 겹치지 않도록 이동
//                ))
//        ));


        Offender0_behaviorTree1.run();
        Offender0_behaviorTree2.run();
        Offender1_behaviorTree1.run();
//        Offender1_behaviorTree2.run();

        return gameState;

    }

}
