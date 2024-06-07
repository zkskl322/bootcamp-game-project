package springboot.profpilot.model.Game;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameAction {
    private String gameId;
    private String playerId;
    private String action;
    private boolean isDone;
}