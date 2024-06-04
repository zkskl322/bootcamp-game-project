package springboot.profpilot.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public String main() {
        return "success";
    }

    @PostMapping("/basic")
    public @ResponseBody String basic() {
        return "basic";
    }


    @GetMapping("/hello")
    public @ResponseBody String hello() {
        return "hello";
    }
}
