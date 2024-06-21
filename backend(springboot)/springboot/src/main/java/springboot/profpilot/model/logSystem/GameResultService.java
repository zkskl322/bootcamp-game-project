package springboot.profpilot.model.logSystem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.GameState;
import springboot.profpilot.model.Gamer.Gamer;
import springboot.profpilot.model.Gamer.GamerService;


@Service
@RequiredArgsConstructor
public class GameResultService {
    private final GameResultRepository gameResultRepository;
    private final GamerService gamerService;

    public GameResult findByPlayer1Name(String player1Name) {
        return gameResultRepository.findByPlayer1Name(player1Name);
    }

    public GameResult findByPlayer2Name(String player2Name) {
        return gameResultRepository.findByPlayer2Name(player2Name);
    }

    public String saveRawDataInMongoDB(GameResult gameResult) {
        gameResultRepository.save(gameResult);
        return "success";
    }

    public GameResult findByGameId(Long gameId) {
        return gameResultRepository.findByGameId(gameId);
    }

    public String saveByGameState(GameState gameState) {
        Gamer gamer1 = gamerService.findByNickname(gameState.getPlayer1Nickname());
        Gamer gamer2 = gamerService.findByNickname(gameState.getPlayer2Nickname());




        GameResult gameResult = findByGameId(Long.parseLong(gameState.getGameId()));
        gameResult.setGameId(Long.parseLong(gameState.getGameId()));
        gameResult.setGameName(gameState.getGameId());
        gameResult.setGameDatetime(gameState.getGameDatetime());

        gameResult.setPlayer1Score(gameState.getScore1());
        gameResult.setPlayer2Score(gameState.getScore2());

        double player1PossessionTime = gameState.getPlayer1_possession_time();
        double player2PossessionTime = gameState.getPlayer2_possession_time();
        double player1_possession = (player1PossessionTime) / (player1PossessionTime + player2PossessionTime) * 100;
        double player2_possession = (player2PossessionTime) / (player1PossessionTime + player2PossessionTime) * 100;
        player1_possession = Math.round(player1_possession * 100) / 100.0;
        player2_possession = Math.round(player2_possession * 100) / 100.0;

        gameResult.setPlayer1Possession(player1_possession);
        gameResult.setPlayer2Possession(player2_possession);

        int player1Shoots = gameState.getPlayer1_shoot_count();
        int player2Shoots = gameState.getPlayer2_shoot_count();
        int player1AvailableShoots = gameState.getPlayer1_available_shoot_count();
        int player2AvailableShoots = gameState.getPlayer2_available_shoot_count();

        gameResult.setPlayer1Shoots(player1Shoots);
        gameResult.setPlayer2Shoots(player2Shoots);

        gameResult.setPlayer1AvailableShoots(player1AvailableShoots);
        gameResult.setPlayer2AvailableShoots(player2AvailableShoots);

        if (gameState.getScore1() > gameState.getScore2()) {
            gameResult.setWinnerName(gamer1.getNickname());
            gameResult.setLoserName(gamer2.getNickname());
            gamer1.setRankPoint(gamer1.getRankPoint() + 100);
            gamer2.setRankPoint(gamer2.getRankPoint() - 100);
        } else if (gameState.getScore1() < gameState.getScore2()) {
            gameResult.setWinnerName(gamer2.getNickname());
            gameResult.setLoserName(gamer1.getNickname());
            gamer1.setRankPoint(gamer1.getRankPoint() - 100);
            gamer2.setRankPoint(gamer2.getRankPoint() + 100);

        } else {
            gameResult.setWinnerName("draw");
            gameResult.setLoserName("draw");
        }

        gameResultRepository.save(gameResult);
        gamerService.save(gamer1);
        gamerService.save(gamer2);
        return "success";
    }
    public GameResult save(GameResult gameResult) {
        return gameResultRepository.save(gameResult);
    }
}
