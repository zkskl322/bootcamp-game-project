package springboot.profpilot.model.MainPage.gameover;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class GameOverController {
    private final GameOverService gameOverService;

    @PostMapping("/restart")
    public String restart() {
        gameOverService.resetGame();
        return "GameRoom";
    }

    @GetMapping("/page/main")
    public String mainpage() {
        return "mainpage";
    }
}
