package springboot.profpilot.model.Game.Team1.Defend;

import springboot.profpilot.model.Game.GamePlayer;
import springboot.profpilot.model.Game.GameState;

//----------------------------
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
// 수비 행동트리 : 지역 수비 기반 알고리즘
// 전반전:
// 0번은 x: 2~4, y: 3.5~6 / 중앙 (3, 4.75)
// 1번은 x: 2~4, y: 1~3.5 / 중앙 (3, 2.25)
// 2번은 x: 0~2, y: 3.5~6 / 중앙 (1, 4.75)
// 3번은 x: 0~2, y: 1~3.5 / 중앙 (1, 2.25)

// 후반전:
// 0번은 x: 7~9, y: 3.5~6 / 중앙 (8, 4.75)
// 1번은 x: 7~9, y: 1~3.5 / 중앙 (8, 2.25)
// 2번은 x: 9~11, y: 3.5~6 / 중앙 (10, 4.75)
// 3번은 x: 9~11, y: 1~3.5 / 중앙 (10, 2.25)

public class Team1DefendGameCondition {
    private GameState gameState;
    public Team1DefendGameCondition(GameState gameState) {
        this.gameState = gameState;
    }
    public boolean isTeamWithBall(int team) {
        return gameState.getWho_has_ball() == team;
    }

    // Offender0 --------------------------------------------------- //
    // 상대팀 선수가 해당 위치에 있지 않은 경우
    public boolean isOpponentInPositionOffender0(int num) {
        if (gameState.getIsFirstHalf() == 1) {
            for (GamePlayer op_gamer : gameState.getPlayer2_players().getPlayers()) {
                if (op_gamer.getPlayer_x() >= 2 && op_gamer.getPlayer_x() <= 4 && op_gamer.getPlayer_y() >= 3.5 && op_gamer.getPlayer_y() <= 6) {
                    if (num == 1) return false;
                    else return true;
                }
            }
            if (num == 1) return true;
            else return false;
        } else {
            for (GamePlayer op_gamer : gameState.getPlayer2_players().getPlayers()) {
                if (op_gamer.getPlayer_x() >= 7 && op_gamer.getPlayer_x() <= 9 && op_gamer.getPlayer_y() >= 3.5 && op_gamer.getPlayer_y() <= 6) {
                    if (num == 1) return false;
                    else return true;
                }
            }
            if (num == 1) return true;
            else return false;

        }
    }
    // Offender0 --------------------------------------------------- //

    // Offender1 --------------------------------------------------- //
    // 상대팀 선수가 해당 위치에 있지 않은 경우
    public boolean isOpponentInPositionOffender1(int num) {
        if (gameState.getIsFirstHalf() == 1) {
            for (GamePlayer op_gamer : gameState.getPlayer2_players().getPlayers()) {
                if (op_gamer.getPlayer_x() >= 2 && op_gamer.getPlayer_x() <= 4 && op_gamer.getPlayer_y() >= 1 && op_gamer.getPlayer_y() <= 3.5) {
                    if (num == 1) return false;
                    else return true;
                }
            }
            if (num == 1) return true;
            else return false;
        } else {
            for (GamePlayer op_gamer : gameState.getPlayer2_players().getPlayers()) {
                if (op_gamer.getPlayer_x() >= 7 && op_gamer.getPlayer_x() <= 9 && op_gamer.getPlayer_y() >= 1 && op_gamer.getPlayer_y() <= 3.5) {
                    if (num == 1) return false;
                    else return true;
                }
            }
            if (num == 1) return true;
            else return false;

        }
    }
    // Offender1 --------------------------------------------------- //

    // Defender2 --------------------------------------------------- //
    // 상대팀 선수가 해당 위치에 있지 않은 경우
    public boolean isOpponentInPositionDefender2(int num) {
        if (gameState.getIsFirstHalf() == 1) {
            for (GamePlayer op_gamer : gameState.getPlayer2_players().getPlayers()) {
                if (op_gamer.getPlayer_x() >= 0 && op_gamer.getPlayer_x() <= 2 && op_gamer.getPlayer_y() >= 3.5 && op_gamer.getPlayer_y() <= 6) {
                    if (num == 1) return false;
                    else return true;
                }
            }
            if (num == 1) return true;
            else return false;
        } else {
            for (GamePlayer op_gamer : gameState.getPlayer2_players().getPlayers()) {
                if (op_gamer.getPlayer_x() >= 9 && op_gamer.getPlayer_x() <= 11 && op_gamer.getPlayer_y() >= 3.5 && op_gamer.getPlayer_y() <= 6) {
                    if (num == 1) return false;
                    else return true;
                }
            }
            if (num == 1) return true;
            else return false;


        }
    }
    // Defender2 --------------------------------------------------- //

    // Defender3 --------------------------------------------------- //
    // 상대팀 선수가 해당 위치에 있지 않은 경우
    public boolean isOpponentInPositionDefender3(int num) {
        if (gameState.getIsFirstHalf() == 1) {
            for (GamePlayer op_gamer : gameState.getPlayer2_players().getPlayers()) {
                if (op_gamer.getPlayer_x() >= 0 && op_gamer.getPlayer_x() <= 2 && op_gamer.getPlayer_y() >= 1 && op_gamer.getPlayer_y() <= 3.5) {
                    if (num == 1) return false;
                    else return true;
                }
            }
            if (num == 1) return true;
            else return false;
        } else {
            for (GamePlayer op_gamer : gameState.getPlayer2_players().getPlayers()) {
                if (op_gamer.getPlayer_x() >= 9 && op_gamer.getPlayer_x() <= 11 && op_gamer.getPlayer_y() >= 1 && op_gamer.getPlayer_y() <= 3.5) {
                    if (num == 1) return false;
                    else return true;
                }
            }
            if (num == 1) return true;
            else return false;
        }
    }
    // Defender3 --------------------------------------------------- //

}
