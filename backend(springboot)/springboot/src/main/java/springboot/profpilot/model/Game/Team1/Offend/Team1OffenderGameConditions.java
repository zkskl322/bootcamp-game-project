package springboot.profpilot.model.Game.Team1.Offend;

import springboot.profpilot.model.Game.GameState;

// 0~1 offense player 2~3 defense player 4 goalkeeper
class Team1OffenderGameConditions {
    private GameState gameState;

    public Team1OffenderGameConditions(GameState gameState) {
        this.gameState = gameState;
    }
    public boolean isTeamWithBall(int team) {
        return gameState.getWho_has_ball() == team;
    }


    // Offender0 --------------------------------------------------- //
    public boolean isOffender0NotPossessBall() {
        return gameState.getPlayer1_control_player() != 0;
    }
    // offender0이 2번과 3번 사이에 있는지 확인
    public boolean isOffender0InBetweenDefender() {
        double player2_x, player2_y, player3_x, player3_y;
        player2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        player2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
        player3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        player3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        double middle_x = (player2_x + player3_x) / 2;
        double middle_y = (player2_y + player3_y) / 2;

        double player0_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
        double player0_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

        if ((player0_x > middle_x - 1 && player0_x < middle_x + 1) && (player0_y > middle_y - 1 && player0_y < middle_y + 1)) {
            return true;
        }
        return false;
    }
    // 중앙이 유망하지 않을 때 true 반환
    public boolean isOffender0CenterGoOkay(int flag) {
        double op_player2_x, op_player2_y, op_player3_x, op_player3_y;
        op_player2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        op_player2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
        op_player3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        op_player3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        double op_middle_x = (op_player2_x + op_player3_x) / 2;
        double op_middle_y = (op_player2_y + op_player3_y) / 2;


        double ball_x = gameState.getPlayer1_x();
        double ball_y = gameState.getPlayer1_y();

        // op_middle과 ball의 직선 방정식 구하기
        double a = (op_middle_y - ball_y) / (op_middle_x - ball_x);
        double b = op_middle_y - a * op_middle_x;

        for (int i = 0; i < 2; i++) {
            double player_x, player_y;
            player_x = gameState.getPlayer2_players().getPlayers().get(i).getPlayer_x();
            player_y = gameState.getPlayer2_players().getPlayers().get(i).getPlayer_y();

            // 점과 직선 사이의 거리 구하기
            double distance = Math.abs(a * player_x - player_y + b) / Math.sqrt(a * a + 1);
            if (distance < 0.3) {
                return flag == 0;
            }
        }
        return flag == 1;
    }
    // Offender0 --------------------------------------------------- //


    // Offender1 --------------------------------------------------- //
    // 1번 공격수가 공을 가지고 있지 않은 경우
    public boolean isOffender1NotPossessBall() {
        return gameState.getPlayer1_control_player() != 1;
    }

    // 이미 1번이 0,2 혹은 1,3 번 중 거리가 더 먼 사이의 공간에 위치한 경우
    public boolean isOffdender1InBetweenOpposite() {
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
                return false;
            } else {
                return true;
            }
        }
        else {
            middle_x = (op_player1_x + op_player3_x) / 2;
            middle_y = (op_player1_y + op_player3_y) / 2;

            double my_player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
            double my_player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();

            if ((my_player1_x > middle_x - 0.1 && my_player1_x < middle_x + 0.1) && (my_player1_y > middle_y - 0.1 && my_player1_y < middle_y + 0.1)) {
                return false;
            } else {
                return true;
            }
        }

    }
    // Offender1 --------------------------------------------------- //


    // Defender2 --------------------------------------------------- //
    // 1번 공격수와 y축으로 대칭한 위치를 가지고, 1번 공격수보다 x축으로 2정도 뒤에 위치
    public boolean isDefender2NotPossessBall() {
        return gameState.getPlayer1_control_player() != 2;
    }

    // 1번 공격수와 y축으로 대칭 및 x축으로 2정도 뒤에 위치한 경우
    public boolean isDefender2SymmetryAndBack2Offender1() {
        double player1_x, player1_y;
        double player2_x, player2_y;

        player1_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
        player1_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();
        player2_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
        player2_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();

        double half_y = 3.5, high_y = 5.2, low_y = 1.8;

        if (gameState.getIsFirstHalf() == 1) {
            double back_x = player1_x - 2;

            if (player1_y < half_y) {
                if (player2_y < high_y + 0.1 && player2_y > high_y - 0.1) {
                    if (player2_x < back_x + 0.1 && player2_x > back_x - 0.1) {
                        return false;
                    }
                }
            } else if (player1_y > half_y) {
                if (player2_y < low_y + 0.1 && player2_y > low_y - 0.1) {
                    if (player2_x < back_x + 0.1 && player2_x > back_x - 0.1) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            double back_x = player1_x + 2;

            if (player1_y < half_y) {
                if (player2_y < high_y + 0.1 && player2_y > high_y - 0.1) {
                    if (player2_x < back_x + 0.1 && player2_x > back_x - 0.1) {
                        return false;
                    }
                }
            } else if (player1_y > half_y) {
                if (player2_y < low_y + 0.1 && player2_y > low_y - 0.1) {
                    if (player2_x < back_x + 0.1 && player2_x > back_x - 0.1) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    // Defender2 --------------------------------------------------- //


    // Defender3 --------------------------------------------------- //
    // 1.2번 수비수와 y축으로 대칭한 위치를 가지고, 2번 수비수보다 x축으로 2정도 뒤에 위치
    public boolean isDefender3NotPossessBall() {
        return gameState.getPlayer1_control_player() != 3;
    }

    // 2번 수비수와 y축으로 대칭 및 x축으로 2정도 뒤에 위치한 경우
    public boolean isDefender3SymmetryDefender2() {
        double player2_x, player2_y;
        double player3_x, player3_y;

        player2_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
        player2_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();
        player3_x = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_x();
        player3_y = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_y();

        double half_y = 3.5, high_y = 5.2, low_y = 1.8;

        if (gameState.getIsFirstHalf() == 1) {
            double back_x = player2_x - 2;

            if (player2_y < half_y) {
                if (player3_y < high_y + 0.1 && player3_y > high_y - 0.1) {
                    if (player3_x < back_x + 0.1 && player3_x > back_x - 0.1) {
                        return false;
                    }
                }
            } else if (player2_y > half_y) {
                if (player3_y < low_y + 0.1 && player3_y > low_y - 0.1) {
                    if (player3_x < back_x + 0.1 && player3_x > back_x - 0.1) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            double back_x = player2_x + 2;

            if (player2_y < half_y) {
                if (player3_y < high_y + 0.1 && player3_y > high_y - 0.1) {
                    if (player3_x < back_x + 0.1 && player3_x > back_x - 0.1) {
                        return false;
                    }
                }
            } else if (player2_y > half_y) {
                if (player3_y < low_y + 0.1 && player3_y > low_y - 0.1) {
                    if (player3_x < back_x + 0.1 && player3_x > back_x - 0.1) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    // Defender3 --------------------------------------------------- //
}



























