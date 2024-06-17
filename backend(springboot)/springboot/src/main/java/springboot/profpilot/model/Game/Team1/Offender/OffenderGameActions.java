package springboot.profpilot.model.Game.Team1.Offender;


import springboot.profpilot.model.Game.GameState;

//----------------------------
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
class OffenderGameActions {
    private GameState gameState;


    // offender0 action ------------------------------------- //
    public OffenderGameActions(GameState gameState) {
        this.gameState = gameState;
    }
    public void MoveBetweenDefender() {

        double op_player2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        double op_player2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
        double op_player3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        double op_player3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        double op_middle_x = (op_player2_x + op_player3_x) / 2;
        double op_middle_y = (op_player2_y + op_player3_y) / 2;
        double player_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
        double player_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

        if (player_x < op_middle_x - 0.1) {
            gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(1);
        } if (player_x > op_middle_x + 0.1) {
            gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(-1);
        } if (player_y < op_middle_y - 0.1) {
            gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(1);
        } if (player_y > op_middle_y + 0.1) {
            gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(-1);
        }
    }
    public void MoveOffender0BetweenAvailablePositionDefender() {
        if (gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x() < 5)
            return;
        if (gameState.getIsFirstHalf() == 1) {
            gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(-1);
        } else {
            gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(1);
        }
    }
    // offender0 action ------------------------------------- //

    // offender1 action ------------------------------------- //


    // offender1 action ------------------------------------- //
}