package springboot.profpilot.model.Game;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamePlayer {
    double player_x;
    double player_y;
    double player_x_speed;
    double player_y_speed;
    int player_direction;
    boolean isPossession;

    public GamePlayer(double player_x, double player_y, int player_direction, double player_x_speed, double player_y_speed) {
        this.player_x = player_x;
        this.player_y = player_y;
        this.player_direction = player_direction;
        this.player_x_speed = player_x_speed;
        this.player_y_speed = player_y_speed;
        this.isPossession = false;
    }
}