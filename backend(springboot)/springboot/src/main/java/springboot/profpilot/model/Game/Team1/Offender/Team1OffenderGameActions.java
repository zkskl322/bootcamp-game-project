package springboot.profpilot.model.Game.Team1.Offender;


import springboot.profpilot.model.Game.GameState;

//----------------------------
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
class Team1OffenderGameActions {
    private GameState gameState;


    // offender0 action ------------------------------------- //
    public Team1OffenderGameActions(GameState gameState) {
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
    public void MoveOffender1BetweenAvailablePositionDefender() {
        double op_player0_x, op_player0_y, op_player1_x, op_player1_y;
        double op_player2_x, op_player2_y, op_player3_x, op_player3_y;

        op_player0_x = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
        op_player0_y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();
        op_player1_x = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_x();
        op_player1_y = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_y();
        op_player2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        op_player2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
        op_player3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        op_player3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        double distance02 = Math.sqrt(Math.pow(op_player0_x - op_player2_x, 2) + Math.pow(op_player0_y - op_player2_y, 2));
        double distance13 = Math.sqrt(Math.pow(op_player1_x - op_player3_x, 2) + Math.pow(op_player1_y - op_player3_y, 2));

        double middle_x, middle_y;
        if (distance02 > distance13) {
            middle_x = (op_player0_x + op_player2_x) / 2;
            middle_y = (op_player0_y + op_player2_y) / 2;

            double my_player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
            double my_player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();

            if ((my_player1_x > middle_x - 0.1 && my_player1_x < middle_x + 0.1) && (my_player1_y > middle_y - 0.1 && my_player1_y < middle_y + 0.1)) {
                return;
            } else {
                if (gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x() < middle_x - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(1);
                } if (gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x() > middle_x + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(-1);
                } if (gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y() < middle_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(1);
                } if (gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y() > middle_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(-1);
                }
            }
        }
        else {
            middle_x = (op_player1_x + op_player3_x) / 2;
            middle_y = (op_player1_y + op_player3_y) / 2;

            double my_player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
            double my_player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();

            if ((my_player1_x > middle_x - 0.1 && my_player1_x < middle_x + 0.1) && (my_player1_y > middle_y - 0.1 && my_player1_y < middle_y + 0.1)) {
                return;
            } else {
                if (gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x() < middle_x - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(1);
                } if (gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x() > middle_x + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(-1);
                } if (gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y() < middle_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(1);
                } if (gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y() > middle_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(-1);
                }
            }
        }
    }

    // offender1 action ------------------------------------- //
}