package springboot.profpilot.model.Game;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamePlayer {
    double player_x;
    double player_y;
    int player_direction;


    public GamePlayer(double player_x, double player_y, int player_direction) {
        this.player_x = player_x;
        this.player_y = player_y;
        this.player_direction = player_direction;
    }
}
