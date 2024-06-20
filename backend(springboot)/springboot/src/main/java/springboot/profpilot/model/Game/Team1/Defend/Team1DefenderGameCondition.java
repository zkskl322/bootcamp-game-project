package springboot.profpilot.model.Game.Team1.Defend;

import springboot.profpilot.model.Game.GameState;

public class Team1DefenderGameCondition {
    private GameState gameState;
    public Team1DefenderGameCondition(GameState gameState) {
        this.gameState = gameState;
    }

    // Offender0 --------------------------------------------------- //
    public boolean isTeamWithBall(int team) {
        return gameState.getWho_has_ball() == team;
    }


}
