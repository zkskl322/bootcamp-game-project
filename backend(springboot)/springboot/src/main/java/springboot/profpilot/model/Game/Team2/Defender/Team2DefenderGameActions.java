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

    public void mirrorFollowOffender(int defenderIndex, int offenderIndex) {
        double offenderX = gameState.getPlayer1_players().getPlayers().get(offenderIndex).getPlayer_x();
        double offenderY = gameState.getPlayer1_players().getPlayers().get(offenderIndex).getPlayer_y();
        double defenderX = gameState.getPlayer2_players().getPlayers().get(defenderIndex).getPlayer_x();
        double defenderY = gameState.getPlayer2_players().getPlayers().get(defenderIndex).getPlayer_y();

        // 상대팀 공격수 위치 기준으로 대칭된 위치 계산
        double newDefenderX = 11 - offenderX; // 경기장 너비가 11
        double newDefenderY = 7 - offenderY;  // 경기장 높이가 7

        // 수비수 위치를 대칭된 위치로 업데이트
        gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_x(newDefenderX);
        gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_y(newDefenderY);

        // 수비수의 속도와 방향을 업데이트하여 자연스럽게 움직이도록 설정 (선택 사항)
        double speedX = (newDefenderX - defenderX) * 0.1; // 이동 속도 계산
        double speedY = (newDefenderY - defenderY) * 0.1; // 이동 속도 계산
        gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_x_speed(speedX);
        gameState.getPlayer2_players().getPlayers().get(defenderIndex).setPlayer_y_speed(speedY);
    }
}
