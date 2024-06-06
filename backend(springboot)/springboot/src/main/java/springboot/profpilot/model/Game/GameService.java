package springboot.profpilot.model.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.DTO.auth.SignUpDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private long lastUpdateTime = System.currentTimeMillis();

    // 게임 상태를 저장하는 맵 (단순 예시, 실제로는 더 복잡할 수 있음)
    private Map<String, GameState> games = new ConcurrentHashMap<>();

    public GameState startGame(String gameId) {
        GameState gameState = new GameState();
        gameState.setGameId(gameId);
        gameState.setGameStatus("STARTED");
        gameState.setScore1(0);
        gameState.setScore2(0);
        gameState.setTime(0);

        gameState.setDirection_x(0.0f);
        gameState.setDirection_y(0.0f);
        gameState.setBall_x(1);
        gameState.setBall_y(5);


        gameState.setPlayer1_x(0.5);
        gameState.setPlayer1_y(5);
        gameState.setPlayer2_x(9.5);
        gameState.setPlayer2_y(5);
        gameState.setPlayer1_direction(3);
        gameState.setPlayer2_direction(4);
        gameState.setPlayer1_possession(false);
        gameState.setPlayer2_possession(false);


        games.put(gameId, gameState);
        return gameState;
    }

    public GameState endGame(String gameId) {
        GameState gameState = games.get(gameId);
        if (gameState == null) {
            return null;
        }
        gameState.setGameStatus("ENDED");
        games.remove(gameId);
        return gameState;
    }


    public void processAction(GameAction action) {
        GameState gameState = games.get(action.getGameId());
        if (gameState == null) {
            return ;
        }

        int player = Integer.parseInt(action.getPlayerId());


        // 플레이어 위치 가져오기
        double player_x = 0, player_y = 0;
        double ball_x = gameState.getBall_x(), ball_y = gameState.getBall_y();
        int player_direction = 0;
        boolean player_possession = false;

        if (player == 1) {
            player_x = gameState.getPlayer1_x();
            player_y = gameState.getPlayer1_y();

            player_direction = gameState.getPlayer1_direction();
            player_possession = gameState.isPlayer1_possession();
        } else {
            player_x = gameState.getPlayer2_x();
            player_y = gameState.getPlayer2_y();

            player_direction = gameState.getPlayer2_direction();
            player_possession = gameState.isPlayer2_possession();
        }

        // 슛하면 공의 속도를 플레이어의 방향으로 설정해서 날라가게 설정
        if (action.getAction().equals("SHOOT")) {
            if (player_possession) {
                double direction_x = 0, direction_y = 0;
                switch (player_direction) {
                    case 1:
                        direction_x = 0;
                        direction_y = -2;
                        break;
                    case 2:
                        direction_x = 0;
                        direction_y = 2;
                        break;
                    case 3:
                        direction_x = -2;
                        direction_y = 0;
                        break;
                    case 4:
                        direction_x = 2;
                        direction_y = 0;
                        break;
                }
                gameState.setDirection_x(direction_x);
                gameState.setDirection_y(direction_y);
                gameState.setPlayer1_possession(false);
                gameState.setPlayer2_possession(false);
                return;
            }
        }



        if (!player_possession) {
            // 플레이어 근처에 공이 있으면 플레이어의 진행 방향의 1만큼 앞에 공을 가지고 있으면서 소유
            if (Math.abs(player_x - ball_x) < 0.2 && Math.abs(player_y - ball_y) < 0.2) {
                if (player_direction == 1 && ball_y > player_y) {
                    player_possession = true;
                } else if (player_direction == 2 && ball_y < player_y) {
                    player_possession = true;
                } else if (player_direction == 3 && ball_x > player_x) {
                    player_possession = true;
                } else if (player_direction == 4 && ball_x < player_x) {
                    player_possession = true;
                }
            } else {
                player_possession = false;
            }
        }

        // 1: up, 2: down 3: left, 4: right
        switch (action.getAction()) {
            case "UP":
                player_y -= 0.3;
                player_direction = 1;
                if (player_possession) {
                    ball_x = player_x;
                    ball_y = player_y - 0.2;
                }
                break;
            case "DOWN":
                player_y += 0.3;
                player_direction = 2;
                if (player_possession) {
                    ball_x = player_x;
                    ball_y = player_y + 0.2;
                }
                break;
            case "LEFT":
                player_x -= 0.3;
                player_direction = 3;
                if (player_possession) {
                    ball_x = player_x - 0.2;
                    ball_y = player_y;
                }
                break;
            case "RIGHT":
                player_x += 0.3;
                player_direction = 4;
                if (player_possession) {
                    ball_x = player_x + 0.2;
                    ball_y = player_y;
                }
                break;
        }

        if (player == 1) {
            gameState.setPlayer1_x(player_x);
            gameState.setPlayer1_y(player_y);
            gameState.setPlayer1_direction(player_direction);
            gameState.setPlayer1_possession(player_possession);
        } else {
            gameState.setPlayer2_x(player_x);
            gameState.setPlayer2_y(player_y);
            gameState.setPlayer2_direction(player_direction);
            gameState.setPlayer2_possession(player_possession);
        }
        if (player_possession) {
            gameState.setBall_x(ball_x);
            gameState.setBall_y(ball_y);
        }
    }

    public GameState getGameState(String gameId) {
        return games.get(gameId);
    }

    public GameState UpdateGameBall(GameState gameState, double deltaTime) {
        double direction_x = gameState.getDirection_x();
        double direction_y = gameState.getDirection_y();
        double ball_x = gameState.getBall_x();
        double ball_y = gameState.getBall_y();
        double leftover_x = 0, leftover_y = 0;

        // 1. 정규화
//        double magnitude = Math.sqrt(direction_x * direction_x + direction_y * direction_y);
//        if (magnitude != 0) {
//            direction_x = direction_x / magnitude;
//            direction_y = direction_y / magnitude;
//        }

        // 공의 저항을 추가함
        direction_x = direction_x * 0.9;
        direction_y = direction_y * 0.9;


        gameState.setDirection_x(direction_x);
        gameState.setDirection_y(direction_y);

        // 2. 공 이동 [deltaTime 을 곱하는 이유: deltaTime 만큼 이동해야 1초에 5의 속도로 이동함]
        ball_x += direction_x * deltaTime * 5;
        ball_y += direction_y * deltaTime * 5;

        // 3. 충돌 처리
        if (ball_x < 0 ) {
            leftover_x = 0 - ball_x;
            ball_x = 0;
        }
        if (ball_x > 10) {
            leftover_x = 10 - ball_x;
            ball_x = 10;
        }
        if (ball_y < 0 ) {
            leftover_y = 0 - ball_y;
            ball_y = 0;
        }
        if (ball_y > 10) {
            leftover_y = 10 - ball_y;
            ball_y = 10;
        }

        // 3. 충돌 처리
        if (ball_x <= 0 || ball_x >= 10) {
            direction_x = -direction_x;
            if (leftover_x != 0) {
                ball_x += direction_x * deltaTime * 5;
            }
            gameState.setDirection_x(direction_x);
        }
        else if (ball_y <= 0 || ball_y >= 10) {
            direction_y = -direction_y;
            if (leftover_y != 0) {
                ball_y += direction_y * deltaTime * 5;
            }
            gameState.setDirection_y(direction_y);
        }


        // 4. 게임 결과 처리
        gameState.setBall_x(ball_x);
        gameState.setBall_y(ball_y);
        return gameState;
    }

    // AI 로직짤 수 있다면 짜자.
    public GameState UpdateGamePlayer(GameState gameState, double deltaTime) {
        double Player1_x = gameState.getPlayer1_x();
        double Player1_y = gameState.getPlayer1_y();
        double Player2_x = gameState.getPlayer2_x();
        double Player2_y = gameState.getPlayer2_y();

        return gameState;
    }


    public GameState updateGameState(String gameId, GameState gameState, double deltaTime) {
        gameState = UpdateGameBall(gameState, deltaTime);
        gameState = UpdateGamePlayer(gameState, deltaTime);
        return gameState;
    }

    @Scheduled(fixedRate = 16) // 약 60 FPS (16ms)
    public void updateGameStates() {
        long currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - lastUpdateTime) / 1000.0; // 초 단위로 변환
        lastUpdateTime = currentTime;

        games.forEach((gameId, gameState) -> {
            // 게임 상태 업데이트 로직
            gameState = updateGameState(gameId, gameState, deltaTime);
            messagingTemplate.convertAndSend("/topic/game/" + gameId, gameState);
        });
    }
}