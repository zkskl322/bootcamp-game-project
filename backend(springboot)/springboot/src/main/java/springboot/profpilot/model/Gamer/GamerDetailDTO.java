package springboot.profpilot.model.Gamer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamerDetailDTO {
    private String nickname;
    private Long winScore;
    private Long loseScore;
    private Long drawScore;

    public GamerDetailDTO(String nickname, Long winScore, Long loseScore, Long drawScore) {
        this.nickname = nickname;
        this.winScore = winScore;
        this.loseScore = loseScore;
        this.drawScore = drawScore;
    }
}