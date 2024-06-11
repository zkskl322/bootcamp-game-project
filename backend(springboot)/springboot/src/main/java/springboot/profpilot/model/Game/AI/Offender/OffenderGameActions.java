package springboot.profpilot.model.Game.AI.Offender;


import springboot.profpilot.model.Game.GameState;

// 0~1 offense player 2~3 defense player 4 goalkeeper
class OffenderGameActions {
    private GameState gameState;

    public OffenderGameActions(GameState gameState) {
        this.gameState = gameState;
    }

//----------------------------
//|    3  1            1 3   |
//| 4         vs           5 |
//|    2  0            0 2   |
//----------------------------
    public void SameTeamOffenderMoveBall(int playerId, int team) {
        double move_ai_x, move_ai_y, op_offense_x, op_offense_y, op_defense_x, op_defense_y;
        int move_ai = playerId == 0 ? 1 : 0;

        // 공격수의 위치는 상대팀 공격수와 수비수의 중간에 위치하도록 한다.
        if (team == 1) {
            move_ai_x = gameState.getPlayer1_players().getPlayers().get(move_ai).getPlayer_x();
            move_ai_y = gameState.getPlayer1_players().getPlayers().get(move_ai).getPlayer_y();
            if (move_ai == 0) {
                op_offense_x = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
                op_offense_y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();
                op_defense_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
                op_defense_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
            } else {
                op_offense_x = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_x();
                op_offense_y = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_y();
                op_defense_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
                op_defense_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();
            }
        } else {
            move_ai_x = gameState.getPlayer2_players().getPlayers().get(move_ai).getPlayer_x();
            move_ai_y = gameState.getPlayer2_players().getPlayers().get(move_ai).getPlayer_y();
            if (move_ai == 0) {
                op_offense_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
                op_offense_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();
                op_defense_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
                op_defense_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();
            } else {
                op_offense_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
                op_offense_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();
                op_defense_x = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_x();
                op_defense_y = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_y();
            }
        }
        System.out.println("Ai Player: " + playerId + " Ai X: " + move_ai_x);
        System.out.println("Opponent Offense X: " + op_offense_x);
        System.out.println("Opponent Defense X: " + op_defense_x);
        System.out.println("Opponent Mid X: " + (op_offense_x + op_defense_x) / 2);

        if (team == 1) {
            if (move_ai_x < (op_offense_x + op_defense_x) / 2 - 0.2) {
                System.out.println("Small Move Right");
                gameState.getPlayer1_players().getPlayers().get(move_ai).setPlayer_x_speed(1);
                return ;
            } if (move_ai_x > (op_offense_x + op_defense_x) / 2 + 0.2) {
                System.out.println("Large Move Right");
                gameState.getPlayer1_players().getPlayers().get(move_ai).setPlayer_x_speed(-1);

            }
        } else {
            if (move_ai_x < (op_offense_x + op_defense_x) / 2 - 0.2) {
                System.out.println("Small Move Right");
                gameState.getPlayer1_players().getPlayers().get(move_ai).setPlayer_x_speed(1);
                return ;
            } if (move_ai_x > (op_offense_x + op_defense_x) / 2 + 0.2) {
                System.out.println("Large Move Right");
                gameState.getPlayer1_players().getPlayers().get(move_ai).setPlayer_x_speed(-1);
            }
        }
        if (team == 2) {
            if (move_ai_y < (op_offense_y + op_defense_y) / 2 - 0.2) {
                System.out.println("Small Move Down");
                gameState.getPlayer2_players().getPlayers().get(move_ai).setPlayer_y_speed(1);
                return ;
            } if (move_ai_y > (op_offense_y + op_defense_y) / 2 + 0.2) {
                System.out.println("Large Move Down");
                gameState.getPlayer2_players().getPlayers().get(move_ai).setPlayer_y_speed(-1);
            }
        } else {
            if (move_ai_y < (op_offense_y + op_defense_y) / 2 - 0.2) {
                System.out.println("Small Move Down");
                gameState.getPlayer2_players().getPlayers().get(move_ai).setPlayer_y_speed(1);
                return ;
            } if (move_ai_y > (op_offense_y + op_defense_y) / 2 + 0.2) {
                System.out.println("Large Move Down");
                gameState.getPlayer2_players().getPlayers().get(move_ai).setPlayer_y_speed(-1);
            }

        }
    }

    public void shoot() {
        System.out.println("Shooting");
    }

    public void passToTeammate() {
        System.out.println("Passing to teammate");
    }

    public void moveTowardsBall() {
        System.out.println("Moving towards ball");
    }
}