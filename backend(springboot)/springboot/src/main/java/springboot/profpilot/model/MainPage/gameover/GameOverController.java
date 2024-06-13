package springboot.profpilot.model.MainPage.gameover;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameOverController {
    private final GameOverService gameOverService;

    @PostMapping("/restart")
    public String restart() {
        gameOverService.resetAndSaveGame();
        gameOverService.resetGame();
        return "GameRoom";

    }

    @GetMapping("/page/gameover")
    public String mainpage() {
        return "mainpage";
    }
}
