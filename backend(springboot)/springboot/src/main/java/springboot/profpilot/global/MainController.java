package springboot.profpilot.global;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import springboot.profpilot.model.DTO.page.MainPageDTO;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {


    @GetMapping("/test/connection")
    @ResponseBody
    public String testConnection() {
        return "Connection Success";
    }

    @GetMapping("/sendToken/{token}")
    @ResponseBody
    public Map<String, String> sendToken(@PathVariable String token, HttpServletResponse res) {
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

}
