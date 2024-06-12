package springboot.profpilot.model.MainPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
public class MainPageController {
    private final MainPageService mainPageService;

    @GetMapping("/main")
    public MainPageDTO getMainPage() {
        return mainPageService.getMainPageData();
    }
}
