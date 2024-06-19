package springboot.profpilot.model.Game.Team2.Defender;

import springboot.profpilot.model.Game.GameState;

//---------------------------- 11
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
//                           7
public class Team2DefenderAlgorithm {
    public void update(GameState gameState) {
        Team2DefenderGameAction defendergameAction = new Team2DefenderGameAction(gameState);
        Team2DefenderGameCondition defenderGameCondition = new Team2DefenderGameCondition(gameState);
    }
}