package springboot.profpilot.model.Game.AI.Offender;

import springboot.profpilot.model.Game.GameState;

// 0~1 offense player 2~3 defense player 4 goalkeeper
class OffenderGameConditions {
    private GameState gameState;

    public OffenderGameConditions(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isTeamWithBall(int team) {
        return gameState.getWho_has_ball() == team;
    }

    public boolean isOtherOffenderControlBall(int team) {
        return (gameState.getPlayer1_control_player() == 0 && gameState.getWho_has_ball() == team) ||
                (gameState.getPlayer1_control_player() == 1 && gameState.getWho_has_ball() == team);
    }

    public boolean isNearBall() {
        // Logic to check if the player is near the ball
        return true;
    }

    public boolean isNearGoal() {
        // Logic to check if the player is near the goal
        return false;
    }

    public boolean isTeammateInBetterPosition() {
        // Logic to check if a teammate is in a better position
        return true;
    }
}