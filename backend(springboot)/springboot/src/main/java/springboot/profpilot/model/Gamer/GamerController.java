package springboot.profpilot.model.Gamer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class GamerController {
    private final GamerService gamerService;

    @Getter
    @Setter
    class GamerForm {
        @NotEmpty(message = "이메일을 입력하십시오.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;
        @NotEmpty(message = "닉네임을 입력하십시오")
        private String nickname;
        @NotEmpty(message = "이름을 입력하십시오")
        private String realname;
        @NotEmpty(message = "비밀번호를 입력하십시오")
        private String password;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignUpDTO signUpDTO) {
//        if (bindingResult.hasErrors()) {
//            return "signup";
//        }

        gamerService.save(signUpDTO.getEmail(), signUpDTO.getNickname(), signUpDTO.getRealname(), signUpDTO.getPassword());
//        gamerService.save(gamerForm.getNickname(), gamerForm.getNickname(), gamerForm.getPassword(), gamerForm.getEmail());
        return "Success";
    }

//    @PostMapping("/login")
//    public String login(@RequestBody LoginDTO loginDTO) {
//        boolean isAuthenticated = gamerService.login(loginDTO.getNickname(), loginDTO.getPassword());
//        if (isAuthenticated) {
//            return "redirect:/main";
//        } else {
//            return "login";
//        }
//    }
//    @GetMapping("/gamers")
//    public List<GamerDTO> getGamers() {
//        return gamerService.getGamerData();
//    }
}
