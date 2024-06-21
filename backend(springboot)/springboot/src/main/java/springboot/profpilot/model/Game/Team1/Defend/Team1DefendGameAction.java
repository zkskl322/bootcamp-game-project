package springboot.profpilot.model.Game.Team1.Defend;

import springboot.profpilot.model.Game.GamePlayer;
import springboot.profpilot.model.Game.GameState;
import springboot.profpilot.model.Gamer.SignUpDTO;


//----------------------------
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------
// 수비 행동트리 : 지역 수비 기반 알고리즘
// 전반전:
// 0번은 x: 2~4, y: 3.5~6 / 중앙 (3, 4.75)
// 1번은 x: 2~4, y: 1~3.5 / 중앙 (3, 2.25)
// 2번은 x: 0~2, y: 3.5~6 / 중앙 (1, 4.75)
// 3번은 x: 0~2, y: 1~3.5 / 중앙 (1, 2.25)

// 후반전:
// 0번은 x: 7~9, y: 3.5~6 / 중앙 (8, 4.75)
// 1번은 x: 7~9, y: 1~3.5 / 중앙 (8, 2.25)
// 2번은 x: 9~11, y: 3.5~6 / 중앙 (10, 4.75)
// 3번은 x: 9~11, y: 1~3.5 / 중앙 (10, 2.25)

public class Team1DefendGameAction {
    private GameState gameState;


    public Team1DefendGameAction(GameState gameState) {
        this.gameState = gameState;
    }


    // Offender0 --------------------------------------------------- //
    public void moveToPositionOffender0() {
        if (gameState.getIsFirstHalf() == 1) {
            double player0_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
            double player0_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

            if (player0_x < 2.9) {
                gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(1);
            } else if (player0_x > 3.1) {
                gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(-1);
            }

            if (player0_y < 4.65) {
                gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(1);
            } else if (player0_y > 4.85) {
                gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(-1);
            }
        } else {
            double player0_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
            double player0_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

            if (player0_x < 7.9) {
                gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(1);
            } else if (player0_x > 8.1) {
                gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(-1);
            }

            if (player0_y < 4.65) {
                gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(1);
            } else if (player0_y > 4.85) {
                gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(-1);
            }
        }
    }

    public void followOpponentOffender0() {
        if (gameState.getIsFirstHalf() == 1) {
            for (GamePlayer gamePlayer : gameState.getPlayer2_players().getPlayers()) {
                if (gamePlayer.getPlayer_x() >= 2 && gamePlayer.getPlayer_x() <= 4 && gamePlayer.getPlayer_y() >= 3.5 && gamePlayer.getPlayer_y() <= 6) {
                    double player0_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
                    double player0_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

                    double opponent_x = gamePlayer.getPlayer_x();
                    double opponent_y = gamePlayer.getPlayer_y();

                    if (player0_x < opponent_x - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(1);
                    } else if (player0_x > opponent_x + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(-1);
                    }

                    if (player0_y < opponent_y - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(1);
                    } else if (player0_y > opponent_y + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(-1);
                    }
                    return ;
                }
            }
        } else {
            for (GamePlayer gamePlayer : gameState.getPlayer2_players().getPlayers()) {
                if (gamePlayer.getPlayer_x() >= 7 && gamePlayer.getPlayer_x() <= 9 && gamePlayer.getPlayer_y() >= 3.5 && gamePlayer.getPlayer_y() <= 6) {
                    double player0_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
                    double player0_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

                    double opponent_x = gamePlayer.getPlayer_x();
                    double opponent_y = gamePlayer.getPlayer_y();

                    if (player0_x < opponent_x - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(1);
                    } else if (player0_x > opponent_x + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(0).setPlayer_x_speed(-1);
                    }

                    if (player0_y < opponent_y - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(1);
                    } else if (player0_y > opponent_y + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(0).setPlayer_y_speed(-1);
                    }
                    return ;
                }
            }

        }
    }
    // Offender0 --------------------------------------------------- //

    // Offender1 --------------------------------------------------- //
    public void moveToPositionOffender1() {
        if (gameState.getIsFirstHalf() == 1) {
            double player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
            double player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();

            if (player1_x < 2.9) {
                gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(1);
            } else if (player1_x > 3.1) {
                gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(-1);
            }

            if (player1_y < 2.15) {
                gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(1);
            } else if (player1_y > 2.35) {
                gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(-1);
            }
        } else {
            double player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
            double player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();

            if (player1_x < 7.9) {
                gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(1);
            } else if (player1_x > 8.1) {
                gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(-1);
            }

            if (player1_y < 2.15) {
                gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(1);
            } else if (player1_y > 2.35) {
                gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(-1);
            }
        }
    }

    public void followOpponentOffender1() {
        if (gameState.getIsFirstHalf() == 1) {
            for (GamePlayer gamePlayer : gameState.getPlayer2_players().getPlayers()) {
                if (gamePlayer.getPlayer_x() >= 2 && gamePlayer.getPlayer_x() <= 4 && gamePlayer.getPlayer_y() >= 1 && gamePlayer.getPlayer_y() <= 3.5) {
                    double player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
                    double player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();

                    double opponent_x = gamePlayer.getPlayer_x();
                    double opponent_y = gamePlayer.getPlayer_y();

                    if (player1_x < opponent_x - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(1);
                    } else if (player1_x > opponent_x + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(-1);
                    }

                    if (player1_y < opponent_y - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(1);
                    } else if (player1_y > opponent_y + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(-1);
                    }
                    return;
                }
            }
        } else {
            for (GamePlayer gamePlayer : gameState.getPlayer2_players().getPlayers()) {
                if (gamePlayer.getPlayer_x() >= 7 && gamePlayer.getPlayer_x() <= 9 && gamePlayer.getPlayer_y() >= 1 && gamePlayer.getPlayer_y() <= 3.5) {
                    double player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
                    double player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();

                    double opponent_x = gamePlayer.getPlayer_x();
                    double opponent_y = gamePlayer.getPlayer_y();

                    if (player1_x < opponent_x - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(1);
                    } else if (player1_x > opponent_x + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(1).setPlayer_x_speed(-1);
                    }

                    if (player1_y < opponent_y - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(1);
                    } else if (player1_y > opponent_y + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(1).setPlayer_y_speed(-1);
                    }
                    return;
                }
            }

        }
    }
    // Offender1 --------------------------------------------------- //

    // Defender2 --------------------------------------------------- //
    public void moveToPositionDefender2() {
        if (gameState.getIsFirstHalf() == 1) {
            double player2_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
            double player2_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();

            if (player2_x < 0.9) {
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(1);
            } else if (player2_x > 1.1) {
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(-1);
            }

            if (player2_y < 4.65) {
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(1);
            } else if (player2_y > 4.85) {
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(-1);
            }
        } else {
            double player2_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
            double player2_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();

            if (player2_x < 9.9) {
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(1);
            } else if (player2_x > 10.1) {
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(-1);
            }

            if (player2_y < 4.65) {
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(1);
            } else if (player2_y > 4.85) {
                gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(-1);
            }
        }
    }

    public void followOpponentDefender2() {
        if (gameState.getIsFirstHalf() == 1) {
            for (GamePlayer gamePlayer : gameState.getPlayer2_players().getPlayers()) {
                if (gamePlayer.getPlayer_x() >= 0 && gamePlayer.getPlayer_x() <= 2 && gamePlayer.getPlayer_y() >= 3.5 && gamePlayer.getPlayer_y() <= 6) {
                    double player2_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
                    double player2_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();

                    double opponent_x = gamePlayer.getPlayer_x();
                    double opponent_y = gamePlayer.getPlayer_y();

                    if (player2_x < opponent_x - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(1);
                    } else if (player2_x > opponent_x + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(-1);
                    }

                    if (player2_y < opponent_y - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(1);
                    } else if (player2_y > opponent_y + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(-1);
                    }
                    return;
                }
            }
        } else {
            for (GamePlayer gamePlayer : gameState.getPlayer2_players().getPlayers()) {
                if (gamePlayer.getPlayer_x() >= 9 && gamePlayer.getPlayer_x() <= 11 && gamePlayer.getPlayer_y() >= 3.5 && gamePlayer.getPlayer_y() <= 6) {
                    double player2_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
                    double player2_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();

                    double opponent_x = gamePlayer.getPlayer_x();
                    double opponent_y = gamePlayer.getPlayer_y();

                    if (player2_x < opponent_x - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(1);
                    } else if (player2_x > opponent_x + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(2).setPlayer_x_speed(-1);
                    }

                    if (player2_y < opponent_y - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(1);
                    } else if (player2_y > opponent_y + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(2).setPlayer_y_speed(-1);
                    }
                    return;
                }
            }
        }
    }
    // Defender2 --------------------------------------------------- //

    // Defender3 --------------------------------------------------- //
    public void moveToPositionDefender3() {
        if (gameState.getIsFirstHalf() == 1) {
            double player3_x = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_x();
            double player3_y = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_y();

            if (player3_x < 0.9) {
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(1);
            } else if (player3_x > 1.1) {
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(-1);
            }

            if (player3_y < 2.15) {
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(1);
            } else if (player3_y > 2.35) {
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(-1);
            }
        } else {
            double player3_x = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_x();
            double player3_y = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_y();

            if (player3_x < 9.9) {
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(1);
            } else if (player3_x > 10.1) {
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(-1);
            }

            if (player3_y < 2.15) {
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(1);
            } else if (player3_y > 2.35) {
                gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(-1);
            }
        }
    }

    public void followOpponentDefender3() {
        if (gameState.getIsFirstHalf() == 1) {
            for (GamePlayer gamePlayer : gameState.getPlayer2_players().getPlayers()) {
                if (gamePlayer.getPlayer_x() >= 0 && gamePlayer.getPlayer_x() <= 2 && gamePlayer.getPlayer_y() >= 1 && gamePlayer.getPlayer_y() <= 3.5) {
                    double player3_x = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_x();
                    double player3_y = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_y();

                    double opponent_x = gamePlayer.getPlayer_x();
                    double opponent_y = gamePlayer.getPlayer_y();

                    if (player3_x < opponent_x - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(1);
                    } else if (player3_x > opponent_x + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(-1);
                    }

                    if (player3_y < opponent_y - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(1);
                    } else if (player3_y > opponent_y + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(-1);
                    }
                    return;
                }
            }
        } else {
            for (GamePlayer gamePlayer : gameState.getPlayer2_players().getPlayers()) {
                if (gamePlayer.getPlayer_x() >= 9 && gamePlayer.getPlayer_x() <= 11 && gamePlayer.getPlayer_y() >= 1 && gamePlayer.getPlayer_y() <= 3.5) {
                    double player3_x = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_x();
                    double player3_y = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_y();

                    double opponent_x = gamePlayer.getPlayer_x();
                    double opponent_y = gamePlayer.getPlayer_y();

                    if (player3_x < opponent_x - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(1);
                    } else if (player3_x > opponent_x + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(3).setPlayer_x_speed(-1);
                    }

                    if (player3_y < opponent_y - 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(1);
                    } else if (player3_y > opponent_y + 0.1) {
                        gameState.getPlayer1_players().getPlayers().get(3).setPlayer_y_speed(-1);
                    }
                    return;
                }
            }
        }
    }
    // Defender3 --------------------------------------------------- //
}

