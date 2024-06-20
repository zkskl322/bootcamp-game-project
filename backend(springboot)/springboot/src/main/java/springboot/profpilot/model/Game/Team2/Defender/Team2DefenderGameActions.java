package springboot.profpilot.model.Game.Team2.Defender;
import springboot.profpilot.model.Game.GameState;
//    1                2
//----------------------------11
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
//                           7
public class Team2DefenderGameActions {
    private GameState gameState;

    public Team2DefenderGameActions(GameState gameState) {
        this.gameState = gameState;
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


        // 상대팀 공격수 위치 기준으로 대칭된 위치 계산
//        double newDefenderX = 11 - offenderX; // 경기장 너비가 11
//        double newDefenderY = 7 - offenderY;  // 경기장 높이가 7

//        gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_x(newDefenderX);
//        gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_y(newDefenderY);

        // 수비수 위치를 대칭된 위치로 업데이트
        // 수비수의 속도와 방향을 업데이트하여 자연스럽게 움직이도록 설정
//        if (defenderX < newDefenderX) {
//            gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_x_speed(1);
//        } else if (defenderX > newDefenderX) {
//            gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_x_speed(-1);
//        } else {
//            gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_x_speed(0);
//        }
//
//        if (defenderY < newDefenderY) {
//            gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_y_speed(1);
//        } else if (defenderY > newDefenderY) {
//            gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_y_speed(-1);
//        } else {
//            gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_y_speed(0);
//        }
    }
}
