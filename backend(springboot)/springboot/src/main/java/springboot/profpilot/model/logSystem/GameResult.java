package springboot.profpilot.model.logSystem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GameResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;
    private String gameName;
    private String gameDatetime;
    private String gameStatus;

    private String player1Name;
    private String player2Name;

    private String winnerName;
    private String loserName;

    private int player1Score;
    private int player2Score;

    private double player1Possession;
    private double player2Possession;

    private int player1Shoots;
    private int player2Shoots;

    private int player1AvailableShoots;
    private int player2AvailableShoots;

    private int player1MatchRankPoints;
    private int player2MatchRankPoints;


}
