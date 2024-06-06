package springboot.profpilot.model.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


// who_has_ball:                    0: no one, 1: player1, 2: player2
// player_control_player:           1. player_offender1, 2. player_offender2, 3. player_defender1, 4. player_defender2
// player_direction:                0: stop, 1: up, 2: down 3: left, 4: right


@Service
public class GameService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private long lastUpdateTime = System.currentTimeMillis();

    // 게임 상태를 저장하는 맵 (단순 예시, 실제로는 더 복잡할 수 있음)
    private Map<String, GameState> games = new ConcurrentHashMap<>();

    public GameState startGame(String gameId) {

        GameState gameState = new GameState();

        // 게임 초기화 ------------------------ //
        gameState.setGameId(gameId);
        gameState.setGameStatus("STARTED");
        gameState.setScore1(0);
        gameState.setScore2(0);
        gameState.setTime(0);
        gameState.setWho_has_ball(0);
        // ------------------------------------ //

        // 공 초기화 ------------------------ //
        gameState.setBall_direction_x(0.0f);
        gameState.setBall_direction_y(0.0f);
        gameState.setBall_x(1);
        gameState.setBall_y(5);
        // ------------------------------------ //


        // player1 setting ------------------------ //
        gameState.setPlayer1_control_player(0);
        gameState.setPlayer1_x(1.5);
        gameState.setPlayer1_y(7);
        gameState.setPlayer1_direction(3);
        gameState.setPlayer1_possession(false);

        GameSoccerTeam player1_players = new GameSoccerTeam();

        List<GamePlayer> players1 = player1_players.getPlayers();
        // 0. player_offender1 1. player_offender2 2. player_defender1 3. player_defender2
        players1.add(new GamePlayer(1.5, 7, 3));
        players1.add(new GamePlayer(1.5, 3, 3));
        players1.add(new GamePlayer(0.5, 7, 3));
        players1.add(new GamePlayer(0.5, 3, 3));

        player1_players.setPlayers(players1);
        gameState.setPlayer1_players(player1_players);
        // ---------------------------------------- //


        // player2 setting ------------------------ //
        gameState.setPlayer2_control_player(0);
        gameState.setPlayer2_x(8.5);
        gameState.setPlayer2_y(7);
        gameState.setPlayer2_direction(4);
        gameState.setPlayer2_possession(false);

        GameSoccerTeam player2_players = new GameSoccerTeam();

        List<GamePlayer> players2 = player2_players.getPlayers();
        // 0. player_offender1 1. player_offender2 2. player_defender1 3. player_defender2
        players2.add(new GamePlayer(8.5, 7, 4)); // player_offender1
        players2.add(new GamePlayer(8.5, 3, 4)); // player_offender2
        players2.add(new GamePlayer(9.5, 7, 4)); // player_defender1
        players2.add(new GamePlayer(9.5, 3, 4)); // player_defender2

        player2_players.setPlayers(players2);
        gameState.setPlayer2_players(player2_players);
        // ---------------------------------------- //

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

    // 게이머의 행동을 처리하는 메소드
    public void processAction(GameAction action) {

        // 1. 게임이 존재하는지 확인
        GameState gameState = games.get(action.getGameId());
        if (gameState == null) {
            return ;
        }

        // 2. 게임 정보 가져오기

        // 2-1. 플레이어 정보 가져오기
        int player = Integer.parseInt(action.getPlayerId());

        double player_x, player_y;
        int player_direction;
        boolean player_possession = false;

        if (player == 1) {
            player_x = gameState.getPlayer1_x();
            player_y = gameState.getPlayer1_y();

            player_direction = gameState.getPlayer1_direction();
            player_possession = gameState.isPlayer1_possession();
        }
        else {
            player_x = gameState.getPlayer2_x();
            player_y = gameState.getPlayer2_y();

            player_direction = gameState.getPlayer2_direction();
            player_possession = gameState.isPlayer2_possession();
        }

        // 2-2. 공 정보 가져오기
        double ball_x = gameState.getBall_x(), ball_y = gameState.getBall_y();


        // 3. 슛 처리
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
                gameState.setBall_direction_x(direction_x);
                gameState.setBall_direction_y(direction_y);
                gameState.setPlayer1_possession(false);
                gameState.setPlayer2_possession(false);


                // error: 공을 가지고 있는 플레이어가 슛을 했을 때, 공과 가장 가까운 플레이어에가 조종(플레이)하는 플레이어가 됨
                // 위에 로직은 에러가 있음 -> 공을 찼을 때 가까운 유저가 다음 유저가 되는 것보단,
                // 플레이어1이 슛을 했을 때
                if (player == 1) {
                    double move_ball_x = gameState.getBall_x();
                    double move_ball_y = gameState.getBall_y();

                    if (player_direction == 1) {
                        move_ball_y -= 1.4;
                    } else if (player_direction == 2) {
                        move_ball_y += 1.4;
                    } else if (player_direction == 3) {
                        move_ball_x -= 1.4;
                    } else if (player_direction == 4) {
                        move_ball_x += 1.4;
                    }

                    double min_distance = Integer.MAX_VALUE;
                    int index = 0, count = 0;

                    for (GamePlayer next_player : gameState.getPlayer1_players().getPlayers()) {
                        double distance = Math.sqrt(Math.pow(next_player.getPlayer_x() - move_ball_x, 2) + Math.pow(next_player.getPlayer_y() - move_ball_y, 2));
                        if (distance < min_distance) {
                            min_distance = distance;
                            index = count;
                        }
                        count++;
                    }

                    // 지금까지 움직인 축구 선수의 위치를 저장
                    gameState.getPlayer1_players().getPlayers().get(gameState.getPlayer1_control_player()).setPlayer_x(gameState.getPlayer1_x());
                    gameState.getPlayer1_players().getPlayers().get(gameState.getPlayer1_control_player()).setPlayer_y(gameState.getPlayer1_y());
                    gameState.getPlayer1_players().getPlayers().get(gameState.getPlayer1_control_player()).setPlayer_direction(gameState.getPlayer1_direction());


                    // 다음 플레이어의 위치를 내가 조종하는 플레이어로 변경
                    GamePlayer next_player = gameState.getPlayer1_players().getPlayers().get(index);
                    gameState.setPlayer1_x(next_player.getPlayer_x());
                    gameState.setPlayer1_y(next_player.getPlayer_y());
                    gameState.setPlayer1_control_player(index);
                    gameState.setPlayer1_direction(next_player.getPlayer_direction());
                } else {
                    double move_ball_x = gameState.getBall_x();
                    double move_ball_y = gameState.getBall_y();

                    if (player_direction == 1) {
                        move_ball_y -= 1.4;
                    } else if (player_direction == 2) {
                        move_ball_y += 1.4;
                    } else if (player_direction == 3) {
                        move_ball_x -= 1.4;
                    } else if (player_direction == 4) {
                        move_ball_x += 1.4;
                    }

                    double min_distance = Integer.MAX_VALUE;
                    int index = 0, count = 0;

                    for (GamePlayer next_player : gameState.getPlayer2_players().getPlayers()) {
                        double distance = Math.sqrt(Math.pow(next_player.getPlayer_x() - move_ball_x, 2) + Math.pow(next_player.getPlayer_y() - move_ball_y, 2));
                        if (distance < min_distance) {
                            min_distance = distance;
                            index = count;
                        }
                        count++;
                    }

                    // 지금까지 움직인 축구 선수의 위치를 저장
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_x(gameState.getPlayer2_x());
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_y(gameState.getPlayer2_y());
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_direction(gameState.getPlayer2_direction());
                }


                gameState.setWho_has_ball(0);
                return;
            }
        } else {
            if (!player_possession) {
                // 플레이어 근처에 공이 있으면 플레이어의 진행 방향의 1만큼 앞에 공을 가지고 있으면서 소유
                if (Math.abs(player_x - ball_x) < 0.3 && Math.abs(player_y - ball_y) < 0.3) {
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


            //
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
    }

    public GameState getGameState(String gameId) {
        return games.get(gameId);
    }

    public GameState UpdateGameBall(GameState gameState, double deltaTime) {
        double direction_x = gameState.getBall_direction_x();
        double direction_y = gameState.getBall_direction_y();
        double ball_x = gameState.getBall_x();
        double ball_y = gameState.getBall_y();
        double leftover_x = 0, leftover_y = 0;

        // 공의 저항을 추가함
        direction_x = direction_x * 0.9;
        direction_y = direction_y * 0.9;


        gameState.setBall_direction_x(direction_x);
        gameState.setBall_direction_y(direction_y);

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
            gameState.setBall_direction_x(direction_x);
        }
        else if (ball_y <= 0 || ball_y >= 10) {
            direction_y = -direction_y;
            if (leftover_y != 0) {
                ball_y += direction_y * deltaTime * 5;
            }
            gameState.setBall_direction_y(direction_y);
        }


        // 4. 게임 결과 처리
        gameState.setBall_x(ball_x);
        gameState.setBall_y(ball_y);
        return gameState;
    }

    // AI 로직 추가 필요
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