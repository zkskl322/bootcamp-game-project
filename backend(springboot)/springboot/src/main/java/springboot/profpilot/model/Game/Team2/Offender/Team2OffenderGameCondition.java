package springboot.profpilot.model.Game.Team2.Offender;

import springboot.profpilot.model.Game.GamePlayer;
import springboot.profpilot.model.Game.GameState;

public class Team2OffenderGameCondition {
    private GameState gameState;
    public Team2OffenderGameCondition(GameState gameState) {
        this.gameState = gameState;
    }

    // 팀2가 공을 가지고 있는지 확인
    public boolean team2hasAball() {
        return gameState.getWho_has_ball() == 2;
    }

    // 조건2. 수비수, 2번이 공을 가지고 있지 않은 경우
    public boolean team2Defender2hasNotBall() {
        return gameState.getPlayer2_players().getPlayers().get(2).getHas_ball() == false;
    }

    // 공격수 3번이 중앙에 있는지 확인
    public boolean isOffender3InCenter() {
        double offender3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        double offender3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();
        return offender3_x == 5.5 && offender3_y == 3.5;
    }
        public boolean isTeamWithBall(int team) {
        return gameState.getWho_has_ball() == team;
    }

    public boolean isTeam2Offender0WithBall() {
            int number = gameState.getPlayer2_control_player();
            return number == 0;
    }

    public boolean isTeam2Offender0RunningToTeam1() {
        if (gameState.getIsFirstHalf() == 1) {
            double offenderZoneBoundary1 = 1.5; // 상대방 수비진 영역 시작
            double offenderZoneBoundary2 = 5.5; // 상대방 수비진 영역 끝
            GamePlayer offender0 = gameState.getPlayer2_players().getPlayers().get(0);
            double x = offender0.getPlayer_x();

//            System.out.println("First Half - offender0 Position: " + x + ", Possession: " + offender0.isPossession());
            if (offenderZoneBoundary1 <= x && x <= offenderZoneBoundary2) return true;
            else return false;

        } else {
                double offenderZoneBoundary1 = 5.5; // 상대방 수비진 영역 시작
                double offenderZoneBoundary2 = 10.5; // 상대방 수비진 영역 끝
                GamePlayer offender0 = gameState.getPlayer2_players().getPlayers().get(0);
                double x = offender0.getPlayer_x();
//                System.out.println("Second Half - offender0 Position: " + x + ", Possession: " + offender0.isPossession());
                if (offenderZoneBoundary1 <= x && x <= offenderZoneBoundary2) return true;
                else return false;
//                double playerX = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
//                return playerX < 5.5; // Team1 쪽으로 뛰고 있는지 확인
        }

    }

    public boolean isTeam2Defender3WithBall() {
        int number = gameState.getPlayer2_control_player();
        return number == 3;
    }

    public boolean isTeam2Offender1WithBall() {
        int number = gameState.getPlayer2_control_player();
        return number == 1; // 조건: 우리팀 공격수 1번이 공을 가지고 있다
    }
    
    public boolean isTeam2Offender1InOffensiveZone() {
        if (gameState.getIsFirstHalf() == 1) {
            double x = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_x();
            return x < 5.0; // 조건: 우리팀 공격수 1번이 x값 5.0(전반 기준)보다 작은 수로 들어간다
        } else {
            double x = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_x();
            return x > 6.0;
        }
    }
}