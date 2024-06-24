package springboot.profpilot.model.Gamer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankDTO {
    private String nickname;
    private int win;
    private int lose;
    private int draw;
    private int rankPoint;
    private String tier;


    public RankDTO(String nickname, int win, int lose, int draw, int rankPoint, String tier) {
        this.nickname = nickname;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.rankPoint = rankPoint;
        this.tier = tier;
    }
}
