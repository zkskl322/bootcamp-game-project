package springboot.profpilot.model.Gamer;

public class whoAmIDTO {
    private String nickname;
    private int winScore;
    private int loseScore;
    private int drawScore;
    private String tier;

    public whoAmIDTO(String nickname, int winScore, int loseScore, int drawScore, String tier) {
        this.nickname = nickname;
        this.winScore = winScore;
        this.loseScore = loseScore;
        this.drawScore = drawScore;
        this.tier = tier;
    }
}
