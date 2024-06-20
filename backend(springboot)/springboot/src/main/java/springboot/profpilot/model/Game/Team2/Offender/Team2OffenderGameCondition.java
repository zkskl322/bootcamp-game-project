package springboot.profpilot.model.Game.Team2.Offender;

import springboot.profpilot.model.Game.GameState;

public class Team2OffenderGameCondition {
    private GameState gameState;
    private Team2OffenderGameAction gameAction;

    public Team2OffenderGameCondition(GameState gameState) {
        this.gameState = gameState;
        this.gameAction = new Team2OffenderGameAction(gameState);
    }

    // 팀2가 공을 가지고 있는지 확인
    public void updateDefender3Position() {
        if (gameState.getWho_has_ball() == 2) {
            gameAction.moveDefender3ToCenter();
        } else {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_x_speed(0);
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(0);
        }
    }
}