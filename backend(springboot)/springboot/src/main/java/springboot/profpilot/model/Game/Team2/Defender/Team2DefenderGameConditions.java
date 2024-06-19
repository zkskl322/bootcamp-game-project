package springboot.profpilot.model.Game.Team2.Defender;
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

    // Defender3
    public boolean isDefender3NotPossessBall() {
        return gameState.getPlayer2_control_player() != 0;
    }

    // 상대방 공격수 0, 1 번이 공을 가지고 있는지 확인
    public boolean isOffenderHasBall() {
        if (gameState.getWho_has_ball() == 1) {
            GamePlayer offender0 = gameState.getPlayer1_players().getPlayers().get(0); // 상대방 팀 공격수 0번
            GamePlayer offender1 = gameState.getPlayer1_players().getPlayers().get(1); // 상대방 팀 공격수 1번

            return offender0.isPossession() || offender1.isPossession();
        }
        return false;
    }

    // 상대방 공격수가 우리팀 수비수가 있는 공간에 들어왔는지 확인
    public boolean isOffenderInDefenseZone() {


        return false;
    }

}