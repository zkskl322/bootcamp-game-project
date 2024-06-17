package springboot.profpilot.model.Game.Team1.Defender;

import springboot.profpilot.model.Game.GameState;

public class Team1DefenderAlgorithm {
    public void update(GameState gameState) {
        Team1DefenderGameAction defenderGameAction = new Team1DefenderGameAction(gameState);
        Team1DefenderGameCondition defenderGameCondition = new Team1DefenderGameCondition(gameState);



    }
}
