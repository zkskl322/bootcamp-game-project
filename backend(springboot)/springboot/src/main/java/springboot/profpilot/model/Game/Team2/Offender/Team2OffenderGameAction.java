package springboot.profpilot.model.Game.Team2.Offender;

import springboot.profpilot.model.Game.GameState;

//----------------------------
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
public class Team2OffenderGameAction {
    private GameState gameState;
    private static final double centerX = 5.5;
    private static final double centerY = 3.5;

    public Team2OffenderGameAction(GameState gameState) {
        this.gameState = gameState;
    }

    // 팀2가 공을 가지고 있을 때 3번 수비수 움직임
    public void moveDefender3ToCenter() {
        double defender3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        double defender3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        if(defender3_x < centerX - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_x_speed(1);
        } else if(defender3_x > centerX + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_x_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_x_speed(0);
        }

        if(defender3_y < centerY - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(1);
        } else if(defender3_y > centerY + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(0);
        }
    }
}