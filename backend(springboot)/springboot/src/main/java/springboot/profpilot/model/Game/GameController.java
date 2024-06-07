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

        if (action.getAction().equals("START_GAME"))
            gameService.startGame(action.getGameId());
        else if (action.getAction().equals("END_GAME"))
            gameService.endGame(action.getGameId());
        gameService.processAction(action);
    }
}