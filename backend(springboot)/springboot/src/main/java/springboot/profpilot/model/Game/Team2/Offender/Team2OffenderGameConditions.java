package springboot.profpilot.model.Game.Team2.Offender;

import springboot.profpilot.model.Game.GameState;

public class Team2OffenderGameConditions {
    private GameState gameState;

    public Team2OffenderGameConditions(GameState gameState) {
        this.gameState = gameState;
    }
    public boolean isTeamWithBall(int team) {
        return gameState.getWho_has_ball() == team;
    }
}
