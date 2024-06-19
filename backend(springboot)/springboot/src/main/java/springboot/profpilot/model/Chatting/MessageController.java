package springboot.profpilot.model.Chatting;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import springboot.profpilot.model.Game.GameService;
import springboot.profpilot.model.Game.GameState;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final GameService gameService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void greeting(Message message) throws Exception {
        Thread.sleep(30); // simulated delay
        messagingTemplate.convertAndSend("/topic/message/" + message.getChattingId(), message);
    }
}