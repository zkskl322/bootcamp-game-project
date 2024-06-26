package springboot.profpilot.model.Game.Team2.Offender;

import springboot.profpilot.model.Game.GamePlayer;
import springboot.profpilot.model.Game.GameState;

public class Team2OffenderGameAction {
    private GameState gameState;
    private final double FIELD_MIN_X = 0.0;
    private final double FIELD_MAX_X = 11.0;
    private final double FIELD_MIN_Y = 0.0;
    private final double FIELD_MAX_Y = 7.0;
    private static final double centerX = 5.5;
    private static final double centerY = 3.5;

    public Team2OffenderGameAction(GameState gameState) {
        this.gameState = gameState;
    }


    // 팀2가 공을 가지고 있을 때 3번 수비수 움직임
    public void moveDefender3ToCenter() {
        double defender3_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        double defender3_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        if(defender3_x < centerX - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_x_speed(1);
        } else if(defender3_x > centerX + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_x_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_x_speed(0);
        }

        if(defender3_y < centerY - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(1);
        } else if(defender3_y > centerY + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(3).setPlayer_y_speed(0);
        }
    }
    public void team2DefenderGotoMid() {
        double defender2_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
        double defender2_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
        double targetX = gameState.getIsFirstHalf() == 1 ? 7.5 : 3.5;
        double targetY = 3.5;

        if(defender2_x < targetX - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(1);
        } else if(defender2_x > targetX + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_x_speed(0);
        }

        if(defender2_y < targetY - 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(1);
        } else if(defender2_y > targetY + 0.1) {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(-1);
        } else {
            gameState.getPlayer2_players().getPlayers().get(2).setPlayer_y_speed(0);
        }
    }

    public void moveOffender1ToDifferentYSameX() {
        // 우리팀 공격수 0번 위치 가져오기
        double offender0_X = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
        double offender0_Y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();

        // 우리팀 공격수 1번 위치 가져오기
        double offender1_X = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_x();
        double offender1_Y = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_y();

        // X
        if (offender0_X - 0.1 > offender1_X) gameState.getPlayer2_players().getPlayers().get(1).setPlayer_x_speed(1);
        else if (offender0_X + 0.1 < offender1_X) gameState.getPlayer2_players().getPlayers().get(1).setPlayer_x_speed(-1);
        // Y
//        if (offender0_Y - 0.1 > offender1_Y) gameState.getPlayer2_players().getPlayers().get(1).setPlayer_y_speed(1);
//        else if (offender0_Y + 0.1 < offender1_Y) gameState.getPlayer2_players().getPlayers().get(1).setPlayer_y_speed(-1);
    }

    public void moveOffender0ToDefender3Diagonal() {
        if (gameState.getIsFirstHalf() == 1) {
        // 우리팀 수비수 3번 위치 가져오기
        double defender3_X = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
        double defender3_Y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

        // 우리팀 공격수 0번 위치 가져오기
        double offender0_X = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
        double offender0_Y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();

        // 대각선 거리 유지
            double targetX = defender3_X - 3;
            double targetY = defender3_Y + 3;

            // 목표 위치가 경기장 범위를 벗어나지 않도록 조정
            targetX = Math.max(FIELD_MIN_X, Math.min(FIELD_MAX_X, targetX));
            targetY = Math.max(FIELD_MIN_Y, Math.min(FIELD_MAX_Y, targetY));

            if (offender0_X - 0.1 > targetX) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(-1);
            } else if (offender0_X + 0.1 < targetX) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(+1);
            }

            if (offender0_Y - 0.1 > targetY) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(-1);
            } else if (offender0_Y + 0.1 < targetY) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(1);
            }

        } else {
            // 우리팀 수비수 3번 위치 가져오기
            double defender3_X = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
            double defender3_Y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();

            // 우리팀 공격수 0번 위치 가져오기
            double offender0_X = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
            double offender0_Y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();

            // 대각선 거리 유지
            double targetX = defender3_X + 3;
            double targetY = defender3_Y + 3;

            // 목표 위치가 경기장 범위를 벗어나지 않도록 조정
            targetX = Math.max(FIELD_MIN_X, Math.min(FIELD_MAX_X, targetX));
            targetY = Math.max(FIELD_MIN_Y, Math.min(FIELD_MAX_Y, targetY));

            if (offender0_X - 0.1 > targetX) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(-1);
            } else if (offender0_X + 0.1 < targetX) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(+1);
            }

            if (offender0_Y - 0.1 > targetY) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(-1);
            } else if (offender0_Y + 0.1 < targetY) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(1);
            }
        }
    }

    public void moveOffender0BasedOnOffender1Y() {
        double offender1_Y = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_y();
        if (offender1_Y < 3.5) {
            moveOffender0BetweenPlayers(2, 0); // y값이 3.5 초과일 때, 공격수 0번이 Team1의 2번과 0번 사이로 이동
        } else {
            moveOffender0BetweenPlayers(3, 1); // y값이 3.5 미만일 때, 공격수 0번이 Team1의 3번과 1번 사이로 이동
        }
    }

    private void moveOffender0BetweenPlayers(int player1Index, int player2Index) {
        if (gameState.getIsFirstHalf() == 1) {
            GamePlayer player1 = gameState.getPlayer1_players().getPlayers().get(player1Index);
            GamePlayer player2 = gameState.getPlayer1_players().getPlayers().get(player2Index);
            GamePlayer offender0 = gameState.getPlayer2_players().getPlayers().get(0);

            double targetX = (player1.getPlayer_x() + player2.getPlayer_x()) / 2;
            double targetY = (player1.getPlayer_y() + player2.getPlayer_y()) / 2;

            targetX = Math.max(FIELD_MIN_X, Math.min(FIELD_MAX_X, targetX));
            targetY = Math.max(FIELD_MIN_Y, Math.min(FIELD_MAX_Y, targetY));

            if (offender0.getPlayer_x() - 0.1 > targetX) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(-1);
            } else if (offender0.getPlayer_x() + 0.1 < targetX) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(1);
            }

            if (offender0.getPlayer_y() - 0.1 > targetY) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(-1);
            } else if (offender0.getPlayer_y() + 0.1 < targetY) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(1);
            }
        } else {
            GamePlayer player1 = gameState.getPlayer1_players().getPlayers().get(player1Index);
            GamePlayer player2 = gameState.getPlayer1_players().getPlayers().get(player2Index);
            GamePlayer offender0 = gameState.getPlayer2_players().getPlayers().get(0);

            double targetX = (player1.getPlayer_x() + player2.getPlayer_x()) / 2;
            double targetY = (player1.getPlayer_y() + player2.getPlayer_y()) / 2;

            targetX = Math.max(FIELD_MIN_X, Math.min(FIELD_MAX_X, targetX));
            targetY = Math.max(FIELD_MIN_Y, Math.min(FIELD_MAX_Y, targetY));

            if (offender0.getPlayer_x() - 0.1 > targetX) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(-1);
            } else if (offender0.getPlayer_x() + 0.1 < targetX) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_x_speed(1);
            }

            if (offender0.getPlayer_y() - 0.1 > targetY) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(-1);
            } else if (offender0.getPlayer_y() + 0.1 < targetY) {
                gameState.getPlayer2_players().getPlayers().get(0).setPlayer_y_speed(1);
            }
        }
    }
}