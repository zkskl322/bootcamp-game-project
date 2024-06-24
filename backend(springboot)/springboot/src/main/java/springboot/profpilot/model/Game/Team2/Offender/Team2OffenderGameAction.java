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

    public void moveDefender2BetweenOffenders() {
        double offender0_x = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
        double offender1_x = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_x();
        double targetX = (offender0_x + offender1_x) / 2;

        double offender0_y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();
        double offender1_y = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_y();
        double targetY = (offender0_y + offender1_y) / 2;

        double defender2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        double defender2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();

        if(defender2_x < targetX - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(1);
        } else if(defender2_x > targetX + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(0);
        }

        if(defender2_y < targetY - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(1);
        } else if(defender2_y > targetY + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(0);
        }
    }

    public void moveDefender2BehindDefender3() {
        double defender3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        double defender3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        double defender2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        double defender2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();

        if(defender2_x < defender3_x - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(1);
        } else if(defender2_x > defender3_x + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(0);
        }

        if(defender2_y < defender3_y - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(1);
        } else if(defender2_y > defender3_y + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(0);
        }
    }

    public void team2DefenderGotoMid() {
        double defender2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        double defender2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
        double targetX = gameState.getIsFirstHalf() == 1 ? 7.5 : 3.5;
        double targetY = 3.5;

        if(defender2_x < targetX - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(1);
        } else if(defender2_x > targetX + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(0);
        }

        if(defender2_y < targetY - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(1);
        } else if(defender2_y > targetY + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(0);
        }
    }
}