package springboot.profpilot.model.Game.Team2.Defender;
import org.springframework.security.core.parameters.P;
import springboot.profpilot.model.Game.GamePlayer;
import springboot.profpilot.model.Game.GameState;
//    1                2
//----------------------------11
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
//                           7
public class Team2DefenderGameConditions {
    private GameState gameState;

    public Team2DefenderGameConditions(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isTeamWithBall(int team) {
        return gameState.getWho_has_ball() == team;
    }

    public boolean isOffender1HasBall() {
        int number = gameState.getPlayer1_control_player();
        return number == 1;

//        GamePlayer offender1 = gameState.getPlayer1_players().getPlayers().get(1); // 상대방 팀 공격수 1번
//        System.out.println("offender1 : " + offender1.isPossession());
//        return offender1.isPossession();
//        return false;
    }

    public boolean isOffender1InDefenseZone() {

        if (gameState.getIsFirstHalf() == 1) {
            double defenseZoneBoundary1 = 7.0; // 수비진 영역
            double defenseZoneBoundary2 = 10.5; // 수비진 영역
            GamePlayer offender1 = gameState.getPlayer1_players().getPlayers().get(1);
            double x = offender1.getPlayer_x();

            if (defenseZoneBoundary1 <= x && x <= defenseZoneBoundary2) return true;
            else return false;
        } else {
            double defenseZoneBoundary1 = 0.5;
            double defenseZoneBoundary2 = 4.0; // 수비진 영역
            GamePlayer offender1 = gameState.getPlayer1_players().getPlayers().get(1);
            double x = offender1.getPlayer_x();

            if (defenseZoneBoundary1 <= x && x <= defenseZoneBoundary2) return true;
            else return false;
        }
    }

}