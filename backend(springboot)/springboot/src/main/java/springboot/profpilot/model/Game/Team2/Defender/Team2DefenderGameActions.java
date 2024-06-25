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
        } else if (player0_x > offender1_x + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(-1);
        }
        if (player0_y < offender1_y - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(1);
        } else if (player0_y > offender1_y + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(-1);
        }
    }

    public void movePlayer2ToY(double targetY) {
        double player2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();

        if (player2_y < targetY - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(1);
        } else if (player2_y > targetY + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(-1);
        }
    }

    public void mirrorFollowOffender() {
        int defenderIndex = 3; // 수비수 인덱스 3번
        int offenderIndex = 1; // 공격수 인덱스 1번

        // 상대팀 공격수 1번 위치 가져오기
        double offenderX = gameState.getPlayer1_players().getPlayers().get(offenderIndex).getPlayer_x();
        double offenderY = gameState.getPlayer1_players().getPlayers().get(offenderIndex).getPlayer_y();

        // 우리팀 수비수 3번 위치 가져오기
        double defenderX = gameState.getPlayer2_players().getPlayers().get(defenderIndex).getPlayer_x();
        double defenderY = gameState.getPlayer2_players().getPlayers().get(defenderIndex).getPlayer_y();

        if (offenderX < defenderX - 0.1) gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_x_speed(-1);
        if (offenderX > defenderX + 0.1) gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_x_speed(1);
        if (offenderY < defenderY - 0.1) gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_y_speed(-1);
        if (offenderY > defenderY + 0.1) gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_y_speed(1);
    }
}