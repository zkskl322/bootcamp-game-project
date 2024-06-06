package springboot.profpilot.model.Game;

import lombok.Getter;
import lombok.Setter;

import javax.swing.text.html.ListView;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameSoccerTeam {

    private List<GamePlayer> players = new ArrayList<>();

}
