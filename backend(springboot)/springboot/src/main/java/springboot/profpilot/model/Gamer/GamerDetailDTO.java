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

    public GamerDetailDTO(String realname, Long winScore, Long loseScore, Long drawScore) {
        this.realname = realname;
        this.winScore = winScore;
        this.loseScore = loseScore;
        this.drawScore = drawScore;
    }
}