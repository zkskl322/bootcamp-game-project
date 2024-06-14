package springboot.profpilot.model.Gamer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;
import springboot.profpilot.global.Utils.GenerateRandomValue;
import springboot.profpilot.model.DTO.auth.CheckEmail;
import springboot.profpilot.model.DTO.auth.VerifyEmail;
import springboot.profpilot.model.emailverfiy.EmailService;
import springboot.profpilot.model.emailverfiy.EmailVerify;
import springboot.profpilot.model.emailverfiy.EmailVerifyService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class GamerController {
    private final GamerService gamerService;
    private final EmailVerifyService emailVerifyService;

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


    @PostMapping("/email/test")
    public String emailTest(@RequestBody VerifyEmail emailDTO) {
        GenerateRandomValue generateRandomValue = new GenerateRandomValue();
        String randomValue = generateRandomValue.getRandomPassword(10);


        // (이메일, 데이터)
        EmailService.sendEmailVerifyCode(emailDTO.getEmail(), randomValue);
        return "Success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup/email/verify")
    public @ResponseBody String verifyEmail(@RequestBody Map<String, String> jsonParam) {
//        String email = jsonParam.substring(10, json_email.length() - 2);
        String email = jsonParam.get("email");
        EmailVerify emailVerify = emailVerifyService.findByEmail(email);

        if (emailVerify != null) {
            String sendTime = emailVerify.getTime();
            if (emailVerify.isVerified()) {
                return "already";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime sendTimeParsed = LocalDateTime.parse(sendTime, formatter);
            LocalDateTime now = LocalDateTime.now();
            String nowString = nowString = now.format(formatter);
            LocalDateTime nowParsed = LocalDateTime.parse(nowString, formatter);
            if (nowParsed.isBefore(sendTimeParsed.plusMinutes(5))) {
                return "wait";
            } else {

                emailVerifyService.deleteByEmail(email);
            }
        }

        String response = gamerService.verifyEmail(email);
        if (response.equals("success")) {
            return "success";
        } else {
            return "fail";
        }
    }

    @PostMapping("/signup/email/verify/check")
    public @ResponseBody String checkEmail(@RequestBody CheckEmail checkEmail) {
        String email = checkEmail.getEmail();
        String code = checkEmail.getVerifyCode();
        return gamerService.checkEmailVerifyCode(email, code);
    }

    @PostMapping("/email/verify")
    public @ResponseBody String changeEmailVerify(@RequestBody VerifyEmail verifyCodeEmail) {
        String email = verifyCodeEmail.getEmail();
        EmailVerify emailVerfiy = emailVerifyService.findByEmail(email);

        if (emailVerfiy != null) {
            String sendTime = emailVerfiy.getTime();
            if (emailVerfiy.isVerified()) {
                return "already";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime sendTimeParsed = LocalDateTime.parse(sendTime, formatter);
            LocalDateTime now = LocalDateTime.now();
            String nowString = now.format(formatter);
            LocalDateTime nowParsed = LocalDateTime.parse(nowString, formatter);
            if (nowParsed.isBefore(sendTimeParsed.plusMinutes(5))) {
                return "wait";
            }
            else {
                emailVerifyService.deleteByEmail(email);
            }
        }

        String response = gamerService.verifyEmail(email);
        if (response.equals("success")) {
            return "success";
        } else {
            return "fail";
        }
    }

    @PostMapping("/email/verify/check")
    public @ResponseBody String checkEmailVerify(@RequestBody CheckEmail checkEmail) {
        System.out.println(checkEmail.getEmail());
        String email = checkEmail.getEmail();
        String code = checkEmail.getVerifyCode();
        return gamerService.checkEmailVerifyCode(email, code);
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
