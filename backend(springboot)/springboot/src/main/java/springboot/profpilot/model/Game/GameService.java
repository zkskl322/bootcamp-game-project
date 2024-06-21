package springboot.profpilot.model.Game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.AI.GoalkeeperAiService;
import springboot.profpilot.model.Game.Action.onPossession.PassAlgorithm;
import springboot.profpilot.model.Game.Team1.Offend.Team1OffenderAlgorithm;
import springboot.profpilot.model.logSystem.GameResultService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


//----------------------------
//|    3  1            1 3   |
//| 4         vs           4 |
//|    2  0            0 2   |
//----------------------------

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private long lastUpdateTime = System.currentTimeMillis();
    private final int time = 0;
    private Map<String, GameState> games = new ConcurrentHashMap<>();
    private final GoalkeeperAiService goalkeeperAiService;
    private final PassAlgorithm passAlgorithm;
    private final Team1OffenderAlgorithm Team1offenderAlgorithm;
    private final GameResultService gameResultService;


    public GameState startGame(String gameId) {


        GameState gameState = new GameState();

        // 게임 초기화 ------------------------ //
        gameState.setGameId(gameId);
        gameState.setGameName("soccer");
        gameState.setGameStatus("STARTED");
        gameState.setGameDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        gameState.setTick(0);

        gameState.setScore1(0);
        gameState.setScore2(0);
        gameState.setWho_has_ball(0);
        gameState.setPlayer1_possession_time(0);
        gameState.setPlayer2_possession_time(0);
        gameState.setPlayer1_shoot_count(0);
        gameState.setPlayer2_shoot_count(0);
        gameState.setPlayer1_available_shoot_count(3);
        gameState.setPlayer2_available_shoot_count(3);

        // 시간 초기화 ------------------------ //
        gameState.setStartTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        gameState.setTime(0);
        gameState.setMax_time(100); // 100초
        gameState.setIsFirstHalf(1);
        gameState.setLast_kicker(-1); // -1: no one, 0: offender1, 1: offender2 2: defender1 3: defender2 4: goalkeeper
        gameState.setLast_passer(-1);
        // ------------------------------------ //

        // 공 초기화 ------------------------ //
        gameState.setBall_direction_x(0.0f);
        gameState.setBall_direction_y(0.0f);
        gameState.setBall_x(5.5); // 중앙에 배치
        gameState.setBall_y(3.5); // 중앙에 배치
        // ------------------------------------ //


        // player1 setting ------------------------ //
        gameState.setPlayer1_control_player(0);
        gameState.setPlayer1_x(1.5);
        gameState.setPlayer1_y(5);
        gameState.setPlayer1_direction(4);
        gameState.setPlayer1_possession(false);

        GameSoccerTeam player1_players = new GameSoccerTeam();

        List<GamePlayer> players1 = player1_players.getPlayers();
        // 0. player_offender1 1. player_offender2 2. player_defender1 3. player_defender2
        players1.add(new GamePlayer(1.5, 5, 4, 0, 0));
        players1.add(new GamePlayer(1.5, 2, 4, 0, 0));
        players1.add(new GamePlayer(0.5, 5, 4, 0, 0));
        players1.add(new GamePlayer(0.5, 2, 4, 0, 0));
        players1.add(new GamePlayer(0.1, 3.5, 4, 0, 0)); // goalkeeper

        player1_players.setPlayers(players1);
        gameState.setPlayer1_players(player1_players);
        // ---------------------------------------- //


        // player2 setting ------------------------ //
        gameState.setPlayer2_control_player(0);
        gameState.setPlayer2_x(9.5);
        gameState.setPlayer2_y(5);
        gameState.setPlayer2_direction(3);
        gameState.setPlayer2_possession(false);
        gameState.setPlayer2_possession_player(-1);
        GameSoccerTeam player2_players = new GameSoccerTeam();

        List<GamePlayer> players2 = player2_players.getPlayers();
        // 0. player_offender1 1. player_offender2 2. player_defender1 3. player_defender2
        players2.add(new GamePlayer(9.5, 5, 3, 0, 0)); // player_offender1
        players2.add(new GamePlayer(9.5, 2, 3, 0, 0)); // player_offender2
        players2.add(new GamePlayer(10.5, 5, 3, 0, 0)); // player_defender1
        players2.add(new GamePlayer(10.5, 2, 3, 0, 0)); // player_defender2
        players2.add(new GamePlayer(10.9, 3.5, 3, 0, 0)); // goalkeeper

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
    public void setGameReset(GameState gameState) {

        gameState.setBall_x(5.5);
        gameState.setBall_y(3.5);
        gameState.setWho_has_ball(0);
        gameState.setBall_direction_x(0);
        gameState.setBall_direction_y(0);

        if (gameState.getIsFirstHalf() == 1) {
            gameState.setPlayer1_x(1.5);
            gameState.setPlayer1_y(5);
            gameState.setPlayer1_direction(1);
            gameState.setPlayer1_control_player(0);
            gameState.setPlayer1_possession(false);

            gameState.setPlayer2_x(9.5);
            gameState.setPlayer2_y(5);
            gameState.setPlayer2_direction(3);
            gameState.setPlayer2_control_player(0);
            gameState.setPlayer2_possession(false);

            List<GamePlayer> players1 = gameState.getPlayer1_players().getPlayers();
            players1.get(0).setPlayer_x(1.5);
            players1.get(0).setPlayer_y(5);
            players1.get(0).setPlayer_direction(1);
            players1.get(1).setPlayer_x(1.5);
            players1.get(1).setPlayer_y(2);
            players1.get(1).setPlayer_direction(1);
            players1.get(2).setPlayer_x(0.5);
            players1.get(2).setPlayer_y(5);
            players1.get(2).setPlayer_direction(1);
            players1.get(3).setPlayer_x(0.5);
            players1.get(3).setPlayer_y(2);
            players1.get(3).setPlayer_direction(1);
            players1.get(4).setPlayer_x(0.1);
            players1.get(4).setPlayer_y(3.5);

            List <GamePlayer> players2 = gameState.getPlayer2_players().getPlayers();
            players2.get(0).setPlayer_x(9.5);
            players2.get(0).setPlayer_y(5);
            players2.get(0).setPlayer_direction(3);
            players2.get(1).setPlayer_x(9.5);
            players2.get(1).setPlayer_y(2);
            players2.get(1).setPlayer_direction(3);
            players2.get(2).setPlayer_x(10.5);
            players2.get(2).setPlayer_y(5);
            players2.get(2).setPlayer_direction(3);
            players2.get(3).setPlayer_x(10.5);
            players2.get(3).setPlayer_y(2);
            players2.get(3).setPlayer_direction(3);
            players2.get(4).setPlayer_x(10.9);
            players2.get(4).setPlayer_y(3.5);


        } else {
            gameState.setPlayer1_x(8.5);
            gameState.setPlayer1_y(5);
            gameState.setPlayer1_direction(3);
            gameState.setPlayer1_control_player(0);
            gameState.setPlayer1_possession(false);

            gameState.setPlayer2_x(1.5);
            gameState.setPlayer2_y(5);
            gameState.setPlayer2_direction(1);
            gameState.setPlayer2_control_player(0);
            gameState.setPlayer2_possession(false);

            List<GamePlayer> players1 = gameState.getPlayer1_players().getPlayers();
            players1.get(0).setPlayer_x(9.5);
            players1.get(0).setPlayer_y(5);
            players1.get(0).setPlayer_direction(3);
            players1.get(1).setPlayer_x(9.5);
            players1.get(1).setPlayer_y(2);
            players1.get(1).setPlayer_direction(3);
            players1.get(2).setPlayer_x(10.5);
            players1.get(2).setPlayer_y(5);
            players1.get(2).setPlayer_direction(3);
            players1.get(3).setPlayer_x(10.5);
            players1.get(3).setPlayer_y(2);
            players1.get(3).setPlayer_direction(3);
            players1.get(4).setPlayer_x(10.9);
            players1.get(4).setPlayer_y(3.5);


            List<GamePlayer> players2 = gameState.getPlayer2_players().getPlayers();
            players2.get(0).setPlayer_x(1.5);
            players2.get(0).setPlayer_y(5);
            players2.get(0).setPlayer_direction(1);
            players2.get(1).setPlayer_x(1.5);
            players2.get(1).setPlayer_y(2);
            players2.get(1).setPlayer_direction(1);
            players2.get(2).setPlayer_x(0.5);
            players2.get(2).setPlayer_y(5);
            players2.get(2).setPlayer_direction(1);
            players2.get(3).setPlayer_x(0.5);
            players2.get(3).setPlayer_y(2);
            players2.get(3).setPlayer_direction(1);
            players2.get(4).setPlayer_x(0.1);
            players2.get(4).setPlayer_y(3.5);
        }
    }
    public void processAction(GameAction action) throws IOException {
        if (action.isDone()) {
            return;
        }
        action.setDone(true);
        // 1. 게임이 존재하는지 확인
        GameState gameState = games.get(action.getGameId());
        if (gameState == null) {
            return ;
        }

        // 2. 게임 정보 가져오기

        // 2-1. 플레이어 정보 가져오기
        int player = Integer.parseInt(action.getPlayerId());

        double player_x, player_y;
        int player_direction, player_control_player;
        boolean player_possession = false;

        if (player == 1) {
            player_x = gameState.getPlayer1_x();
            player_y = gameState.getPlayer1_y();

            player_direction = gameState.getPlayer1_direction();
            player_possession = gameState.isPlayer1_possession();
            player_control_player = gameState.getPlayer1_control_player();
        }
        else {
            player_x = gameState.getPlayer2_x();
            player_y = gameState.getPlayer2_y();

            player_direction = gameState.getPlayer2_direction();
            player_possession = gameState.isPlayer2_possession();
            player_control_player = gameState.getPlayer2_control_player();
        }

        // 2-2. 공 정보 가져오기
        double ball_x = gameState.getBall_x(), ball_y = gameState.getBall_y();

        // 3. 플레이어 이동
        switch (action.getAction()) {
            case "UP":
                if (player_possession) {
                    ball_x = player_x;
                    ball_y = player_y - 0.2;
                    player_y -= 0.05;
                } else {
                    player_y -= 0.08;
                }
                player_direction = 2;
                break;
            case "DOWN":
                if (player_possession) {
                    ball_x = player_x;
                    ball_y = player_y + 0.2;
                    player_y += 0.05;
                } else {
                    player_y += 0.08;
                }
                player_direction = 4;
                break;
            case "LEFT":
                if (player_possession) {
                    ball_x = player_x - 0.2;
                    ball_y = player_y;
                    player_x -= 0.05;
                } else {
                    player_x -= 0.08;
                }
                player_direction = 3;
                break;
            case "RIGHT":
                if (player_possession) {
                    ball_x = player_x + 0.2;
                    ball_y = player_y;
                    player_x += 0.05;
                } else {
                    player_x += 0.08;
                }
                player_direction = 1;
                break;
        }


        // 4. 플레이어가 공을 가지고 있을 때
        if (player_possession) {
            // 공 소유 + S Key = 패스
            if (action.getAction().equals("KEY_S")) {
                int next_player = passAlgorithm.FindPassPlayer(gameState, player);
                if (next_player != -1) {
                    passAlgorithm.PasstheBall(gameState, player, next_player);
                }
                return;
            }

            // 공 소유 + D Key = 슛
            if (action.getAction().equals("KEY_D")) {
                double direction_x = 0, direction_y = 0;
                switch (player_direction) {
                    case 1:
                        direction_x = 2;
                        direction_y = 0;
                        break;
                    case 2:
                        direction_x = 0;
                        direction_y = -2;
                        break;
                    case 3:
                        direction_x = -2;
                        direction_y = 0;
                        break;
                    case 4:
                        direction_x = 0;
                        direction_y = 2;
                        break;
                }
                gameState.setBall_direction_x(direction_x);
                gameState.setBall_direction_y(direction_y);
                gameState.setPlayer1_possession(false);
                gameState.setPlayer2_possession(false);


                // 플레이어1이 슛을 했을 때 플레이어 1의 다음 선수를 지정
                if (player == 1) {
                    double move_ball_x = gameState.getBall_x();
                    double move_ball_y = gameState.getBall_y();

                    if (player_direction == 1) {
                        move_ball_x += 1.4;
                    } else if (player_direction == 2) {
                        move_ball_y -= 1.4;
                    } else if (player_direction == 3) {
                        move_ball_x -= 1.4;
                    } else if (player_direction == 4) {
                        move_ball_y += 1.4;
                    }

                    double min_distance = Integer.MAX_VALUE;
                    int index = 0, count = 0;

                    gameState.setLast_kicker(gameState.getPlayer1_control_player());

                    for (GamePlayer next_player : gameState.getPlayer1_players().getPlayers()) {
                        double distance = Math.sqrt(Math.pow(next_player.getPlayer_x() - move_ball_x, 2) + Math.pow(next_player.getPlayer_y() - move_ball_y, 2));
                        if (distance < min_distance && count != 4) {
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
                }
                // 플레이어2가 슛을 했을 때 플레이어 2의 다음 선수를 지정
                else {
                    double move_ball_x = gameState.getBall_x();
                    double move_ball_y = gameState.getBall_y();

                    if (player_direction == 1) {
                        move_ball_x += 1.4;
                    } else if (player_direction == 2) {
                        move_ball_y -= 1.4;
                    } else if (player_direction == 3) {
                        move_ball_x -= 1.4;
                    } else if (player_direction == 4) {
                        move_ball_y += 1.4;
                    }

                    double min_distance = Integer.MAX_VALUE;
                    int index = 0, count = 0;

                    gameState.setLast_kicker(gameState.getPlayer2_control_player());
                    for (GamePlayer next_player : gameState.getPlayer2_players().getPlayers()) {
                        double distance = Math.sqrt(Math.pow(next_player.getPlayer_x() - move_ball_x, 2) + Math.pow(next_player.getPlayer_y() - move_ball_y, 2));
                        if (distance < min_distance && count != 4) {
                            min_distance = distance;
                            index = count;
                        }
                        count++;
                    }

                    // 지금까지 움직인 축구 선수의 위치를 저장
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_x(gameState.getPlayer2_x());
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_y(gameState.getPlayer2_y());
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_direction(gameState.getPlayer2_direction());


                    // 다음 플레이어의 위치를 내가 조종하는 플레이어로 변경
                    GamePlayer next_player = gameState.getPlayer2_players().getPlayers().get(index);
                    gameState.setPlayer2_x(next_player.getPlayer_x());
                    gameState.setPlayer2_y(next_player.getPlayer_y());
                    gameState.setPlayer2_control_player(index);
                    gameState.setPlayer2_direction(next_player.getPlayer_direction());

                }

                gameState.setWho_has_ball(0);
                return;
            }
        }

        // 5. 플레이어가 공을 가지고 있지 않을 때
        else {

            if (action.getAction().equals("KEY_W")) {
                System.out.println("KEY_W");
            }

            // 공 미소유 + S key = 선수 변경
            if (action.getAction().equals("KEY_S")) {
                int next_control_player1 = (gameState.getPlayer1_control_player() + 1) % 4;
                int next_control_player2 = (gameState.getPlayer2_control_player() + 1) % 4;
                if (player == 1) {
                    gameState.getPlayer1_players().getPlayers().get(gameState.getPlayer1_control_player()).setPlayer_x(gameState.getPlayer1_x());
                    gameState.getPlayer1_players().getPlayers().get(gameState.getPlayer1_control_player()).setPlayer_y(gameState.getPlayer1_y());
                    gameState.getPlayer1_players().getPlayers().get(gameState.getPlayer1_control_player()).setPlayer_direction(gameState.getPlayer1_direction());

                    gameState.setPlayer1_control_player(next_control_player1);
                    gameState.setPlayer1_x(gameState.getPlayer1_players().getPlayers().get(next_control_player1).getPlayer_x());
                    gameState.setPlayer1_y(gameState.getPlayer1_players().getPlayers().get(next_control_player1).getPlayer_y());
                    gameState.setPlayer1_direction(gameState.getPlayer1_players().getPlayers().get(next_control_player1).getPlayer_direction());

                } else {
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_x(gameState.getPlayer2_x());
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_y(gameState.getPlayer2_y());
                    gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_direction(gameState.getPlayer2_direction());

                    gameState.setPlayer2_control_player(next_control_player2);
                    gameState.setPlayer2_x(gameState.getPlayer2_players().getPlayers().get(next_control_player2).getPlayer_x());
                    gameState.setPlayer2_y(gameState.getPlayer2_players().getPlayers().get(next_control_player2).getPlayer_y());
                    gameState.setPlayer2_direction(gameState.getPlayer2_players().getPlayers().get(next_control_player2).getPlayer_direction());
                }
                return;
            }

            // 공 소유 + A Key = 슬라이딩 태클
            if (action.getAction().equals("KEY_D"))
                return;
        }



        if (!player_possession) {
            // 플레이어 근처에 공이 있으면 플레이어의 진행 방향의 1만큼 앞에 공을 가지고 있으면서 소유
            if (Math.abs(player_x - ball_x) < 0.3 && Math.abs(player_y - ball_y) < 0.3) {
                if (player_direction == 1 && ball_x < player_x) {
                    if (player == 1) {
                        player_possession = true;
                        gameState.setPlayer2_possession(false);
                    } else {
                        player_possession = true;
                        gameState.setPlayer2_possession_player(player_control_player);
                        gameState.setPlayer1_possession(false);
                    }
                } else if (player_direction == 2 && ball_y < player_y) {
                    if (player == 1) {
                        player_possession = true;
                        gameState.setPlayer2_possession_player(player_control_player);
                        gameState.setPlayer2_possession(false);
                    } else {
                        player_possession = true;
                        gameState.setPlayer1_possession(false);
                    }
                } else if (player_direction == 3 && ball_x > player_x) {
                    if (player == 1) {
                        player_possession = true;
                        gameState.setPlayer2_possession(false);
                    } else {
                        player_possession = true;
                        gameState.setPlayer1_possession(false);
                    }
                } else if (player_direction == 4 && ball_y > player_y) {
                    if (player == 1) {
                        player_possession = true;
                        gameState.setPlayer2_possession(false);
                    } else {
                        player_possession = true;
                        gameState.setPlayer1_possession(false);
                    }
                }
            }
        }

        if (player == 1) {
            gameState.setPlayer1_x(player_x);
            gameState.setPlayer1_y(player_y);
            gameState.setPlayer1_direction(player_direction);
            gameState.setPlayer1_possession(player_possession);
            if (player_possession) {
                gameState.setWho_has_ball(1);
            }
            gameState.getPlayer1_players().getPlayers().get(gameState.getPlayer1_control_player()).setPlayer_x(gameState.getPlayer1_x());
            gameState.getPlayer1_players().getPlayers().get(gameState.getPlayer1_control_player()).setPlayer_y(gameState.getPlayer1_y());
        }
        else {
            gameState.setPlayer2_x(player_x);
            gameState.setPlayer2_y(player_y);
            gameState.setPlayer2_direction(player_direction);
            gameState.setPlayer2_possession(player_possession);
            if (player_possession) {
                gameState.setWho_has_ball(2);
            }
            gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_x(gameState.getPlayer2_x());
            gameState.getPlayer2_players().getPlayers().get(gameState.getPlayer2_control_player()).setPlayer_y(gameState.getPlayer2_y());
        }

        if (player_possession) {
            gameState.setBall_x(ball_x);
            gameState.setBall_y(ball_y);
        }

    }
    public GameState UpdateGameBall(GameState gameState, double deltaTime) {
        double direction_x = gameState.getBall_direction_x();
        double direction_y = gameState.getBall_direction_y();
        double ball_x = gameState.getBall_x();
        double ball_y = gameState.getBall_y();
        double leftover_x = 0, leftover_y = 0;
        direction_x = direction_x * 0.9;
        direction_y = direction_y * 0.9;

        GameSoccerTeam player1_players = gameState.getPlayer1_players();
        GameSoccerTeam player2_players = gameState.getPlayer2_players();

        // 공의 저항을 추가함


        gameState.setBall_direction_x(direction_x);
        gameState.setBall_direction_y(direction_y);

        // 2. 공 이동 [deltaTime 을 곱하는 이유: deltaTime 만큼 이동해야 1초에 5의 속도로 이동함]
        ball_x += direction_x * deltaTime * 5;
        ball_y += direction_y * deltaTime * 5;

        if (gameState.getWho_has_ball() == 0) {
            int count = -1;
            for (GamePlayer instance_player : player1_players.getPlayers()) {
                count++;
                if (count == gameState.getLast_kicker())
                    continue;
                if (gameState.getLast_passer() != -1 && gameState.getLast_passer() == count)
                    continue;
                if (Math.abs(instance_player.getPlayer_x() - ball_x) < 0.3 && Math.abs(instance_player.getPlayer_y() - ball_y) < 0.3) {
                    gameState.setPlayer1_possession(true);
                    gameState.setPlayer2_possession(false);
                    gameState.setWho_has_ball(1);

                    gameState.setBall_direction_x(0);
                    gameState.setBall_direction_y(0);
                    gameState.setPlayer1_control_player(count);
                    gameState.setPlayer1_x(instance_player.getPlayer_x());
                    gameState.setPlayer1_y(instance_player.getPlayer_y());
                    if (gameState.getPlayer1_direction() == 1) {
                        gameState.setBall_y(instance_player.getPlayer_y() - 0.2);
                    } else if (gameState.getPlayer1_direction() == 2) {
                        gameState.setBall_y(instance_player.getPlayer_y() + 0.2);
                    } else if (gameState.getPlayer1_direction() == 3) {
                        gameState.setBall_x(instance_player.getPlayer_x() - 0.2);
                    } else if (gameState.getPlayer1_direction() == 4) {
                        gameState.setBall_x(instance_player.getPlayer_x() + 0.2);
                    }
                    return gameState;
                }
            }
            count = -1;
            for (GamePlayer player : player2_players.getPlayers()) {
                count++;
                if (count == gameState.getLast_kicker())
                    continue;
                if (gameState.getLast_passer() != -1 && gameState.getLast_passer() == count)
                    continue;
                if (Math.abs(player.getPlayer_x() - ball_x) < 0.3 && Math.abs(player.getPlayer_y() - ball_y) < 0.3) {
                    gameState.setPlayer2_possession(true);
                    gameState.setPlayer1_possession(false);
                    gameState.setWho_has_ball(2);

                    gameState.setBall_direction_x(0);
                    gameState.setBall_direction_y(0);
                    gameState.setPlayer2_control_player(count);
                    gameState.setPlayer2_x(player.getPlayer_x());
                    gameState.setPlayer2_y(player.getPlayer_y());
                    gameState.setPlayer2_possession_player(count);
                    if (gameState.getPlayer2_direction() == 1) {
                        gameState.setBall_y(player.getPlayer_y() - 0.2);
                    } else if (gameState.getPlayer2_direction() == 2) {
                        gameState.setBall_y(player.getPlayer_y() + 0.2);
                    } else if (gameState.getPlayer2_direction() == 3) {
                        gameState.setBall_x(player.getPlayer_x() - 0.2);
                    } else if (gameState.getPlayer2_direction() == 4) {
                        gameState.setBall_x(player.getPlayer_x() + 0.2);
                    }
                    return gameState;
                }
            }
        }
//


        // 3. 충돌 처리 (골 구현)
        if (ball_x <= 0 || ball_x >= 11) {
            if (ball_y >= 3 && ball_y <= 4) {
                if (ball_x <= 0) {
                    if (gameState.getIsFirstHalf() == 1) {
                        gameState.setScore2(gameState.getScore2() + 1);
                    } else {
                        gameState.setScore1(gameState.getScore1() + 1);
                    }
                } else {
                    if (gameState.getIsFirstHalf() == 1) {
                        gameState.setScore1(gameState.getScore1() + 1);

                    } else {
                        gameState.setScore2(gameState.getScore2() + 1);
                    }
                }
                setGameReset(gameState);
                return gameState;
            }
        }

        // 3. 충돌 처리
        if (ball_x < 0 ) {
            leftover_x = 0 - ball_x;
            ball_x = 0;
        }
        if (ball_x > 11) {
            leftover_x = 11 - ball_x;
            ball_x = 11;
        }
        if (ball_y < 0 ) {
            leftover_y = 0 - ball_y;
            ball_y = 0;
        }
        if (ball_y > 7) {
            leftover_y = 7 - ball_y;
            ball_y = 7;
        }

        // 3. 충돌 처리
        if (ball_x <= 0 || ball_x >= 11) {
            direction_x = -direction_x;
            if (leftover_x != 0) {
                ball_x += direction_x * deltaTime * 5;
            }
            gameState.setBall_direction_x(direction_x);
        }
        else if (ball_y <= 0 || ball_y >= 7) {
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
    public GameState UpdateGamePlayer2(GameState gameState, double deltaTime) {
        List<GamePlayer> players_1 = gameState.getPlayer1_players().getPlayers();
        List<GamePlayer> players_2 = gameState.getPlayer2_players().getPlayers();


        for (GamePlayer player : players_1) {
            player.setPlayer_x(player.getPlayer_x() + player.getPlayer_x_speed() * deltaTime);
            player.setPlayer_y(player.getPlayer_y() + player.getPlayer_y_speed() * deltaTime);
            player.setPlayer_x_speed(player.getPlayer_x_speed() * 0.9);
            player.setPlayer_y_speed(player.getPlayer_y_speed() * 0.9);
        } // player1
        for (GamePlayer player : players_2) {
            player.setPlayer_x(player.getPlayer_x() + player.getPlayer_x_speed() * deltaTime);
            player.setPlayer_y(player.getPlayer_y() + player.getPlayer_y_speed() * deltaTime);
            player.setPlayer_x_speed(player.getPlayer_x_speed() * 0.9);
            player.setPlayer_y_speed(player.getPlayer_y_speed() * 0.9);
        } // player2
        return gameState;
    }
    public GameState UpdateGamePlayer3(GameState gameState, double detlaTime) {
        if (gameState.getWho_has_ball() == 1) {
            gameState.setPlayer1_possession_time(gameState.getPlayer1_possession_time() + detlaTime);
        } else if (gameState.getWho_has_ball() == 2) {
            gameState.setPlayer2_possession_time(gameState.getPlayer2_possession_time() + detlaTime);
        }
        return gameState;
    }
    public GameState UpdateGamePlayer(GameState gameState, double deltaTime) {

        gameState = UpdateGamePlayer2(gameState, deltaTime);
        gameState = UpdateGamePlayer3(gameState, deltaTime);
        gameState = goalkeeperAiService.update(gameState);
        gameState = Team1offenderAlgorithm.updateOnPossession(gameState);
        gameState = Team2defenderAlgorithm.updateOnPossession(gameState);
        gameState = Team2offenderAlgorithm.updateOnPossession(gameState);
        return gameState;
    }
    public GameState updateGameState(String gameId, GameState gameState, double deltaTime, Long time) {

        gameState.setTime(gameState.getTime() + deltaTime);
        if (gameState.getTime() > gameState.getMax_time()) {
            gameState.setGameStatus("END");
            messagingTemplate.convertAndSend("/topic/game/" + gameId, gameState);
            gameResultService.saveByGameState(gameState);

            games.remove(gameId);
            return gameState;
        }
        if (gameState.getIsFirstHalf() == 1 && gameState.getTime() > gameState.getMax_time() / 2) {
            gameState.setIsFirstHalf(0);
            setGameReset(gameState);
        }

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
            GameState updatedGameState = updateGameState(gameId, gameState, deltaTime, currentTime);
            messagingTemplate.convertAndSend("/topic/game/" + gameId, updatedGameState);
        });

    }
}
