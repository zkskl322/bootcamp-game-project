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

        if ((player0_x > middle_x - 0.3 && player0_x < middle_x + 0.3) && (player0_y > middle_y - 0.3 && player0_y < middle_y + 0.3)) {
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
            if (distance < 0.2) {
                return flag == 0;
            }
        }
        return flag == 1;
    }


    public boolean isOffeder0InBetweenDefenderButCenterNotOkay() {
        double op_player2_x, op_player2_y, op_player3_x, op_player3_y;
        op_player2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        op_player2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
        op_player3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        op_player3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        double op_middle_x = (op_player2_x + op_player3_x) / 2;
        double op_middle_y = (op_player2_y + op_player3_y) / 2;

        double player0_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
        double player0_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();

        if ((player0_x > op_middle_x - 0.3 && player0_x < op_middle_x + 0.3) && (player0_y > op_middle_y - 0.3 && player0_y < op_middle_y + 0.3)) {
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
                if (distance < 0.2) {
                    return false;
                }
            }
        }
        return true;
    }





}