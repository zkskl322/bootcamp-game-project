package springboot.profpilot.model.Gamer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamerDetailDTO {
    private String realname;
    private Long winScore;
    private Long loseScore;
    private Long drawScore;
    private Double winRate;

    public GamerDetailDTO(String realname, Long winScore, Long loseScore, Long drawScore) {
        this.realname = realname;
        this.winScore = winScore;
        this.loseScore = loseScore;
        this.drawScore = drawScore;
        this.winRate = calculateWinRate(winScore, loseScore, drawScore);
    }

    private Double calculateWinRate(Long winScore, Long loseScore, Long drawScore) {
        if(winScore == null || loseScore == null || drawScore == null) {
            return null;
        }

        Long totalGames = winScore + loseScore + drawScore;
        if(totalGames == 0) {
            return 0.0;
        } else {
            return (double) winScore / totalGames;
        }
    }
}