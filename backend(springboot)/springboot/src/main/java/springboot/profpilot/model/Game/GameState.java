package springboot.profpilot.model.Game;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameState {
    // 게임 정보
    private String  gameId;
    private String  gameStatus;
    private int     score1;
    private int     score2;
    private int     time;


    // 공 정보
    private double  direction_x;
    private double  direction_y;
    private double  ball_x;
    private double  ball_y;


    // 플레이어 정보
    private boolean player1_possession;
    private boolean player2_possession;
    private double  player1_x;
    private double  player1_y;
    private double  player2_x;
    private double  player2_y;
    private int     player1_direction; // 0: stop, 1: up, 2: down 3: left, 4: right
    private int     player2_direction;

    public void update(GameAction action) {
        // 게임 상태 업데이트 로직
    }
}