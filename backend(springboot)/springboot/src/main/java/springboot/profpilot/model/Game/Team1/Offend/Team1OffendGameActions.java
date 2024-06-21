package springboot.profpilot.model.Game.Team1.Offend;


import springboot.profpilot.model.Game.GameState;

//----------------------------
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
class Team1OffendGameActions {
    private GameState gameState;
    public Team1OffendGameActions(GameState gameState) {
        this.gameState = gameState;
    }

    // offender0 action ------------------------------------- //
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


    // defender2 action ------------------------------------- //
    public void MoveDefender2SymmetryAndBack2Offender1() {
        double player1_x, player1_y;
        player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
        player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();
        double player2_x, player2_y;
        player2_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
        player2_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();


        double half_y = 3.5, high_y = 5.2, low_y = 1.8;

        if (gameState.getIsFirstHalf() == 1) {
            double back_x = player1_x - 2;

            if (player1_y < half_y) {
                if (player2_y > high_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(-1);
                }
                if (player2_y < high_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(1);
                }
            }
            if (player1_y > half_y) {
                if (player2_y > low_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(-1);
                }
                if (player2_y < low_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(1);
                }
            }
            if (player2_x > back_x + 0.1) {
                if (player2_x < 0)
                    return;
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(-1);
            }
            if (player2_x < back_x - 0.1) {
                if (player2_x > 11)
                    return;
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(1);
            }
        } else {
            double back_x = player1_x + 2;

            if (player1_y < half_y) {
                if (player2_y > high_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(-1);
                }
                if (player2_y < high_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(1);
                }
            }
            if (player1_y > half_y) {
                if (player2_y > low_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(-1);
                }
                if (player2_y < low_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(1);
                }
            }
            if (player2_x > back_x + 0.1) {
                if (player2_x < 0)
                    return;
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(-1);
            }
            if (player2_x < back_x - 0.1) {
                if (player2_x > 11)
                    return;
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(1);
            }
        }
    }
    // defender2 action ------------------------------------- //

    // defender3 action ------------------------------------- //
    public void MoveDefender3SymmetryDefender2() {
        double player2_x, player2_y;
        player2_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
        player2_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();
        double player3_x, player3_y;
        player3_x = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_x();
        player3_y = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_y();

        double half_y = 3.5, high_y = 5.2, low_y = 1.8;

        if (gameState.getIsFirstHalf() == 1) {
            double back_x = player2_x - 2;

            if (player2_y < half_y) {
                if (player3_y > high_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(-1);
                }
                if (player3_y < high_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(1);
                }
            }
            if (player2_y > half_y) {
                if (player3_y > low_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(-1);
                }
                if (player3_y < low_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(1);
                }
            }
            if (player3_x > back_x + 0.1) {
                if (player3_x < 0)
                    return;
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(-1);
            }
            if (player3_x < back_x - 0.1) {
                if (player3_x > 11)
                    return;
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(1);
            }
        } else {
            double back_x = player2_x + 2;

            if (player2_y < half_y) {
                if (player3_y > high_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(-1);
                }
                if (player3_y < high_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(1);
                }
            }
            if (player2_y > half_y) {
                if (player3_y > low_y + 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(-1);
                }
                if (player3_y < low_y - 0.1) {
                    gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(1);
                }
            }
            if (player3_x > back_x + 0.1) {
                if (player3_x < 0)
                    return;
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(-1);
            }
            if (player3_x < back_x - 0.1) {
                if (player3_x > 11)
                    return;
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(1);
            }
        }
    }
    // defender3 action ------------------------------------- //
}