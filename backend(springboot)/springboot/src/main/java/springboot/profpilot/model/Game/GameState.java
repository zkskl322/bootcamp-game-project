package springboot.profpilot.model.Game;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


// who_has_ball: 0: no one, 1: player1, 2: player2
// player_control_player: 1. player_offender1, 2. player_offender2, 3. player_defender1, 4. player_defender2
// player_direction: 0: stop, 1: up, 2: down 3: left, 4: right



@Setter
@Getter
public class GameState {
    // 게임 정보
    private String  gameId;
    private String  gameName;
    private String  gameStatus;
    private String  gameDatetime;
    private String  player1Nickname;
    private String  player2Nickname;
    private int     tick;


    private int     score1;
    private int     score2;
    private int     who_has_ball; // 0: no one, 1: player1, 2: player2
    private double  player1_possession_time;
    private double  player2_possession_time;
    private int     player1_shoot_count;
    private int     player2_shoot_count;
    private int     player1_available_shoot_count;
    private int     player2_available_shoot_count;

    private String  startTime;
    private double  time;
    private double  max_time;
    private int     isFirstHalf; // 0: second half, 1: first half
    private int     last_kicker; // 0: player1, 1: player2
    private int     last_passer;


    // 공 정보
    private double  ball_direction_x;
    private double  ball_direction_y;
    private double  ball_x;
    private double  ball_y;

    // 플레이어1 정보
    private int     player1_control_player; // 1. player1_offender1, 2. player1_offender2, 3. player1_defender1, 4. player1_defender2
    private double  player1_x;
    private double  player1_y;
    private int     player1_direction; // 1: right, 2. up, 3. left, 4. down
    private boolean player1_possession;
    private GameSoccerTeam player1_players;

    // 플레이어2 정보
    private int     player2_control_player; // 1. player2_offender1, 2. player2_offender2, 3. player2_defender1, 4. player2_defender2
    private double  player2_x;
    private double  player2_y;
    private int     player2_direction;
    private int     player2_possession_player;
    private boolean player2_possession;
    private GameSoccerTeam player2_players;

    public void update(GameAction action) {
        // 게임 상태 업데이트 로직
    }
}