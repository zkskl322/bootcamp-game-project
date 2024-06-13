package springboot.profpilot.model.Gamer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
//    @GetMapping("/gamer/social/signup")
//    public ResponseEntity<String> signupGoogleUser(@AuthenticationPrincipal OAuth2User principal) {
//        String email = principal.getAttribute("email");
//        String username = principal.getAttribute("name");
//
//        Optional<Gamer> existingUser = gamerService.findByEmail(email);
//        if(existingUser.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
//        }
//
//        Gamer gamer = gamerService.registerNewSocialUser(username, email);
//
//        return ResponseEntity.ok("User registered successfully: " + gamer.getNickname());
//    }

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
