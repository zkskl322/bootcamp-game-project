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

}
