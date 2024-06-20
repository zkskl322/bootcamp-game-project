package springboot.profpilot.model.Game.Team2.Defender;

import springboot.profpilot.model.Game.GameState;

public class Team2DefenderGameConditions {
    private GameState gameState;
    public Team2DefenderGameConditions(GameState gameState) {
        this.gameState = gameState;
    }
    public boolean isTeamWithBall(int team) {
        return gameState.getWho_has_ball() == team;
    }

    public boolean isOffender0NearDefender0() {
        double offender0X = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
        double offender0Y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

        double defender0X = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
        double defender0Y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();

        double distance = Math.sqrt(Math.pow(offender0X - defender0X, 2) + Math.pow(offender0Y - defender0Y, 2));

        return distance <= 1.0;
    }
}
