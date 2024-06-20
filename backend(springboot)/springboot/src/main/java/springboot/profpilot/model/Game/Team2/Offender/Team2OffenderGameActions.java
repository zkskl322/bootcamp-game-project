package springboot.profpilot.model.Game.Team2.Offender;

import springboot.profpilot.model.Game.GameState;

public class Team2OffenderGameActions {
    private GameState gameState;

    public Team2OffenderGameActions(GameState gameState) {
        this.gameState = gameState;
    }

    public void moveOffender1ToDifferentYSameX() {

        // 우리팀 공격수 0번 위치 가져오기
        double offender0_X = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
        double offender0_Y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();

        // 우리팀 공격수 1번 위치 가져오기
        double offender1_X = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_x();
        double offender1_Y = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_y();

        if (offender0_X < offender1_X - 0.1) gameState.getPlayer2_players().getPlayers().get(1).setPlayer_x_speed(-1);
        if (offender0_X > offender1_X + 0.1) gameState.getPlayer2_players().getPlayers().get(1).setPlayer_x_speed(1);
        if (offender0_Y < offender1_Y - 0.1) gameState.getPlayer2_players().getPlayers().get(1).setPlayer_x_speed(-1);
        if (offender0_Y > offender1_Y + 0.1) gameState.getPlayer2_players().getPlayers().get(1).setPlayer_x_speed(1);


//        gameState.getPlayer2_players().getPlayers().get(1).setPlayer_x(offender0X);
//        if (offender1Y < offender0Y - 0.1) {
//            gameState.getPlayer2_players().getPlayers().get(1).setPlayer_y_speed(1);
//        } else if (offender1Y > offender0Y + 0.1) {
//            gameState.getPlayer2_players().getPlayers().get(1).setPlayer_y_speed(-1);
//        } else {
//            gameState.getPlayer2_players().getPlayers().get(1).setPlayer_y_speed(0);
//        }
    }
}
