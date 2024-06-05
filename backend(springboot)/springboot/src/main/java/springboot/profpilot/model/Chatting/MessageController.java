package springboot.profpilot.model.Chatting;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {


    @MessageMapping("/message")
    @SendTo("/topic/message")
    public Message greeting(Message message) throws Exception {
        Thread.sleep(30); // simulated delay
        return new Message(HtmlUtils.htmlEscape(message.getContent()), message.getUuid());
    }

}