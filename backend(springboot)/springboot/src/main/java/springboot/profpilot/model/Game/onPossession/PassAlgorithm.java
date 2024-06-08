package springboot.profpilot.model.Game.onPossession;

import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.GameSoccerTeam;
import springboot.profpilot.model.Game.GameState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PassAlgorithm {
    private double getDirectionAngle(int direction) {
        switch (direction) {
            case 1: return 0;    // left
            case 2: return 270;   // up
            case 3: return 180;  // right
            case 4: return 90;  // down
            default: return 0;   // default case
        }
    }

    public int FindPassPlayer(GameState gameState, int player) {
        double player_x = 0, player_y = 0;
        int player_control_player = 0, player_shot_direction = 0;
        GameSoccerTeam player_players = null; // 자신을 포함한 팀원들의 정보를 담고 있는 객체

        if (player == 1) {
            player_x = gameState.getPlayer1_x();
            player_y = gameState.getPlayer1_y();
            player_control_player = gameState.getPlayer1_control_player();
            player_shot_direction = gameState.getPlayer1_direction();
            player_players = gameState.getPlayer1_players();
        } else if (player == 2) {
            player_x = gameState.getPlayer2_x();
            player_y = gameState.getPlayer2_y();
            player_control_player = gameState.getPlayer2_control_player();
            player_shot_direction = gameState.getPlayer2_direction();
            player_players = gameState.getPlayer2_players();
        }

        double minDistance = Double.MAX_VALUE;
        int bestPlayer = -1;

        for (int i = 0; i < player_players.getPlayers().size(); i++) {
            // 현재 컨트롤 중인 선수는 패스 대상에서 제외
            if (i == player_control_player) continue;

            double teammate_x = player_players.getPlayers().get(i).getPlayer_x();
            double teammate_y = player_players.getPlayers().get(i).getPlayer_y();

            // 벡터 (dx, dy)를 구함
            double dx = teammate_x - player_x;
            double dy = teammate_y - player_y;

            // 거리 계산
            double distance = Math.sqrt(dx * dx + dy * dy);

            // 현재 보는 방향과의 각도 차이를 계산
            double angleToTeammate = Math.toDegrees(Math.atan2(dy, dx));
            double relativeAngle = getDirectionAngle(player_shot_direction) - angleToTeammate;

            if (relativeAngle > 180) {
                relativeAngle -= 360;
            } else if (relativeAngle < -180) {
                relativeAngle += 360;
            }

            if (relativeAngle >= -60 && relativeAngle <= 60) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestPlayer = i;
                }
            }
        }

        return bestPlayer;
    }

    public void PasstheBall(GameState gameState, int player, int receiver) {
        double ball_x = 0, ball_y = 0, control_player_x = 0, control_player_y = 0;
        int control_player = 0, control_player_direction = 0;
        GameSoccerTeam player_players = null;


        ball_x = gameState.getBall_x();
        ball_y = gameState.getBall_y();
        if (player == 1) {
            player_players = gameState.getPlayer1_players();
            control_player_x = gameState.getPlayer1_x();
            control_player_y = gameState.getPlayer1_y();
            control_player = gameState.getPlayer1_control_player();
            control_player_direction = gameState.getPlayer1_direction();
        } else if (player == 2) {
            player_players = gameState.getPlayer2_players();
            control_player_x = gameState.getPlayer2_x();
            control_player_y = gameState.getPlayer2_y();
            control_player = gameState.getPlayer2_control_player();
            control_player_direction = gameState.getPlayer2_direction();
        }

        // 공을 패스할 선수의 좌표
        double receiver_x = player_players.getPlayers().get(receiver).getPlayer_x();
        double receiver_y = player_players.getPlayers().get(receiver).getPlayer_y();
        int receiver_direction = player_players.getPlayers().get(receiver).getPlayer_direction();

        // 공의 방향값에 공과 선수의 거리를 계산한 값을 넣어줌

        double dx = receiver_x - ball_x;
        double dy = receiver_y - ball_y;

        // 공과 선수의 거리
        double distance = Math.sqrt(dx * dx + dy * dy);

        // 원하는 패스 거리
        double passDistance = 1.5; // 패스의 길이를 정합니다 (원하는 대로 조정 가능)

//        if (distance < 1.3) {
//            double dist = 2 - Math.abs(dx) - Math.abs(dy);
//
//            if (dx < 0) dx = dx - dist/2;
//            else dx = dx + dist/2;
//
//            if (dy < 0) dy = dy - dist/2;
//            else dy = dy + dist/2;
//
//
//            gameState.setBall_direction_x(dx * passDistance);
//            gameState.setBall_direction_y(dy * passDistance);
//        } else {
//            // 방향 설정
//            gameState.setBall_direction_x(dx * passDistance);
//            gameState.setBall_direction_y(dy * passDistance);
//        }

        // 방향 설정
        gameState.setBall_direction_x(dx * passDistance);
        gameState.setBall_direction_y(dy * passDistance);


        gameState.setWho_has_ball(0);
        gameState.setPlayer1_possession(false);
        gameState.setPlayer2_possession(false);
        gameState.setLast_passer(control_player);

        if (player == 1) {

            // 바뀐 선수의 좌표값을 gameState에 저장
            gameState.getPlayer1_players().getPlayers().get(control_player).setPlayer_x(control_player_x);
            gameState.getPlayer1_players().getPlayers().get(control_player).setPlayer_y(control_player_y);
            gameState.getPlayer1_players().getPlayers().get(control_player).setPlayer_direction(control_player_direction);

            // 조종할 선수를 바꿔줌
            gameState.setPlayer1_control_player(receiver);
            gameState.setPlayer1_x(receiver_x);
            gameState.setPlayer1_y(receiver_y);
            gameState.setPlayer1_direction(receiver_direction);


        }
        else if (player == 2) {
            gameState.setPlayer2_control_player(receiver);

            gameState.getPlayer2_players().getPlayers().get(control_player).setPlayer_x(control_player_x);
            gameState.getPlayer2_players().getPlayers().get(control_player).setPlayer_y(control_player_y);
            gameState.getPlayer2_players().getPlayers().get(control_player).setPlayer_direction(control_player_direction);

            gameState.setPlayer2_control_player(receiver);
            gameState.setPlayer2_x(receiver_x);
            gameState.setPlayer2_y(receiver_y);
            gameState.setPlayer2_direction(receiver_direction);

        }
    }
}
