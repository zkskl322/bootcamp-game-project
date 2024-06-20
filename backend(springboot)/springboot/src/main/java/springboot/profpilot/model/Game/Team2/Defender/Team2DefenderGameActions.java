package springboot.profpilot.model.Game.Team2.Defender;

import springboot.profpilot.model.Game.GameState;

//----------------------------
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
public class Team2DefenderGameActions {
    private GameState gameState;

    public Team2DefenderGameActions(GameState gameState) {
        this.gameState = gameState;
    }

    public void followOpponentOffender() {
        double offender1_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
        double offender1_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

        double player0_x = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
        double player0_y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();

        if (player0_x < offender1_x - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(1);
        } if (player0_x > offender1_x + 0.1) {
            gameState.getPlayer2_players().getPlayers ().get(0).setPlayer_x_speed(-1);
        } if (player0_y < offender1_y - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(1);
        } if (player0_y > offender1_y + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(-1);
        }
    }
}