package springboot.profpilot.model.Game.Math;

import springboot.profpilot.model.Game.GameSoccerTeam;
import springboot.profpilot.model.Game.GameState;

public class PassAlgorithm {


    public int passAlgorithm(GameState gameState, int player) {

        double player_x = 0, player_y = 0;
        int player_control_player = 0, player_shot_direction = 0; // 1. up, 2. down, 3. left, 4. right
        GameSoccerTeam player_players = null;

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
        if (player_shot_direction == 1) player_shot_direction = 2;
        else if (player_shot_direction == 2) player_shot_direction = 4;
        else if (player_shot_direction == 3) player_shot_direction = 1;
        else if (player_shot_direction == 4) player_shot_direction = 3;


        // 기준축 생성 (player_shot_direction을 이용해 방향을 결정)
        double direction_radians = Math.toRadians(player_shot_direction * 90); // 1: 0도, 2: 90도, 3: 180도, 4: 270도
        double base_x = Math.cos(direction_radians);
        double base_y = Math.sin(direction_radians);

        // 패스 가능한 각도 범위 설정
        double pass_angle = Math.toRadians(60); // 60도
        double min_angle = -pass_angle;
        double max_angle = pass_angle;

        // 가장 가까운 선수 찾기
        int closest_player = -1;
        double closest_distance = Double.MAX_VALUE;

        for (int i = 0; i < gameState.getPlayers().length; i++) {
            if (i == player_control_player) continue;

            double teammate_x = gameState.getPlayers()[i].getX();
            double teammate_y = gameState.getPlayers()[i].getY();

            // 선수와 플레이어 간의 벡터 계산
            double vector_x = teammate_x - player_x;
            double vector_y = teammate_y - player_y;

            // 벡터의 각도 계산
            double angle = Math.atan2(vector_y, vector_x) - direction_radians;

            // 각도를 [-pi, pi] 범위로 조정
            if (angle > Math.PI) angle -= 2 * Math.PI;
            if (angle < -Math.PI) angle += 2 * Math.PI;

            // 각도가 패스 가능한 범위에 있는지 확인
            if (angle >= min_angle && angle <= max_angle) {
                double distance = Math.sqrt(vector_x * vector_x + vector_y * vector_y);
                if (distance < closest_distance) {
                    closest_distance = distance;
                    closest_player = i;
                }
            }
        }

//        // 가장 가까운 선수의 번호 반환
        return closest_player;
    }

}
