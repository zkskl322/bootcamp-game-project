package springboot.profpilot.model.User;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Getter
    @Setter
    class UserForm {
        @NotEmpty(message = "이메일을 입력하십시오.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;
        @NotEmpty(message = "닉네임을 입력하십시오")
        private String nickname;
        @NotEmpty(message = "이름을 입력하십시오")
        private String name;
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
}
