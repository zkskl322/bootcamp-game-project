package springboot.profpilot.model.logSystem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.Game.GameState;



@Service
@RequiredArgsConstructor
public class GameResultService {
    private final GameResultRepository gameResultRepository;


    public String saveByGameState(GameState gameState) {
        GameResult gameResult = new GameResult();
        gameResult.setGameId(gameState.getGameId());
        gameResult.setGameName(gameState.getGameId());
        gameResult.setGameDatetime(gameState.getGameDatetime());

        gameResult.setPlayer1Name("player1");
        gameResult.setPlayer2Name("player2");

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
            gameResult.setWinnerName("player1");
            gameResult.setLoserName("player2");
            gameResult.setPlayer1MatchRankPoints(100);
            gameResult.setPlayer2MatchRankPoints(-100);
        } else if (gameState.getScore1() < gameState.getScore2()) {
            gameResult.setWinnerName("player2");
            gameResult.setLoserName("player1");
            gameResult.setPlayer1MatchRankPoints(-100);
            gameResult.setPlayer2MatchRankPoints(100);
        } else {
            gameResult.setWinnerName("draw");
            gameResult.setLoserName("draw");
            gameResult.setPlayer1MatchRankPoints(0);
            gameResult.setPlayer2MatchRankPoints(0);
        }

        gameResultRepository.save(gameResult);
        return "success";
    }

    public String saveRawDataInMongoDB(GameResult gameResult) {
        gameResultRepository.save(gameResult);
        return "success";
    }


    public GameResult save(GameResult gameResult) {
        return gameResultRepository.save(gameResult);
    }
}
