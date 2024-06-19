package springboot.profpilot.model.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private GameService gameService;

    @MessageMapping("/action")
    public void handleAction(GameAction action) throws Exception {

        if (action.getAction().equals("START_GAME")){
            // GameAction으로 player1Nickname, player2Nickname을 받아와서 gameService.startGame()에 넘겨줘야 함
            gameService.startGame(action.getGameId());
        }
        else if (action.getAction().equals("END_GAME"))
            gameService.endGame(action.getGameId());
        gameService.processAction(action);
    }
}