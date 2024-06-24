package springboot.profpilot.model.logSystem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {

    GameResult findByPlayer1Name(String player1Name);
    GameResult findByPlayer2Name(String player2Name);

    GameResult findByGameId(Long gameId);
}
