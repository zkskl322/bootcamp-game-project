package springboot.profpilot.model.Game.Team2.Defender;

import springboot.profpilot.model.Game.GameState;

public class Team2DefenderAlgorithm {
    public void update(GameState gameState) {
        Team2DefenderGameAction defenderGameAction = new Team2DefenderGameAction(gameState);
        Team2DefenderGameCondition defenderGameCondition = new Team2DefenderGameCondition(gameState);

        if (defenderGameCondition.isTeam2WithBall()) {
            defenderGameAction.moveDefender3AlongBallY();
        }
    }
}