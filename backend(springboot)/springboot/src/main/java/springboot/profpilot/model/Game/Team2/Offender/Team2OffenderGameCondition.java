package springboot.profpilot.model.Game.Team2.Offender;

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
}