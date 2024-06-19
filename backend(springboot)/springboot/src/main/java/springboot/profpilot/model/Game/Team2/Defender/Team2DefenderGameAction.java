package springboot.profpilot.model.Game.Team2.Defender;

import springboot.profpilot.model.Game.GameState;

public class Team2DefenderGameAction {
    private GameState gameState;

    public Team2DefenderGameAction(GameState gameState) {
        this.gameState = gameState;
    }

    public void moveDefender3AlongBallY() {
        double ball_y = gameState.getBall_y();
        double play3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        // getFieldWidth가 없다.
//        double halfLine_x = gameState.getFieldWidth() / 2;
        // 축구장의 총 길이는 11(가로), 7(세로)로 설정해 둠
        double halfLine_x = 11/2; // 즉 5.5

        double defender3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();

        // 3번 수비수가 하프라인을 넘지 않도록 제한
        if(defender3_x < halfLine_x) {
            // 공의 Y축을 따라 이동
            double defender3_y = 0;
            if (defender3_y < ball_y - 0.1) {
                gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(1);
            } else if (defender3_y > ball_y + 0.1) {
                gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(-1);
            } else {
                gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(0);
            }
        } else {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_x_speed(0);
        }
    }
}