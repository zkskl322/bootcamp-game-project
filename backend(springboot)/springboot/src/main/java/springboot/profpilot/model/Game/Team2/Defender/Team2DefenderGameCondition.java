package springboot.profpilot.model.Game.Team2.Defender;

import springboot.profpilot.model.Game.GameState;

public class Team2DefenderGameCondition {
    private GameState gameState;

    public Team2DefenderGameCondition(GameState gameState) {
        this.gameState = gameState;
    }

    // 팀2가 공을 가지고 있는지 확인
    public boolean isTeam2WithBall() {
        return gameState.getWho_has_ball() == 2;
    }
}