package springboot.profpilot.model.Gamer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springboot.profpilot.global.Utils.GenerateRandomValue;
import springboot.profpilot.model.DTO.auth.CheckEmail;
import springboot.profpilot.model.DTO.auth.VerifyEmail;
import springboot.profpilot.model.emailverfiy.EmailService;
import springboot.profpilot.model.emailverfiy.EmailVerify;
import springboot.profpilot.model.emailverfiy.EmailVerifyService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class GamerController {
    private final GamerService gamerService;
    private final EmailVerifyService emailVerifyService;



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

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignUpDTO signUpDTO) {
        Gamer gamer = gamerService.findByNickname(signUpDTO.getNickname());
        EmailVerify emailVerify = emailVerifyService.findByEmail(signUpDTO.getEmail());

        if (emailVerify == null || !emailVerify.isVerified())
            return "Email not verified";
        if (gamer != null)
            return "already exists";
        gamerService.save(signUpDTO.getEmail(), signUpDTO.getNickname(), signUpDTO.getRealname(), signUpDTO.getPassword());
        return "Success";
    }

    @PostMapping("/signup/email/verify")
    public @ResponseBody String verifyEmail(@RequestBody Map<String, String> jsonParam) {
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

    @PostMapping("/find-id/email/verify")
    public @ResponseBody String findIdEmailVerify(@RequestBody Map<String, String> jsonParam) {
        String email = jsonParam.get("email");
        String response = gamerService.verifyEmail(email);
        return response;
    }

    @PostMapping("/find-id/email/verify/check")
    public @ResponseBody String checkFindIdEmail(@RequestParam String email, @RequestParam String code) {
        String verifyResult = gamerService.checkEmailVerifyCode(email, code);
        if ("success".equals(verifyResult)) {
            String nickname = gamerService.findNicknameByEmail(email);
            if (nickname != null) {
                return nickname;
            } else {
                return "Email not found";
            }
        }
        return "Verification failed";
    }

    @GetMapping("/find-id/email/verify/check/Id")
    public @ResponseBody String showingId(@RequestParam String email) {
        // 이메일로 닉네임 찾기
        String nickname = gamerService.findNicknameByEmail(email);
        if (nickname != null) {
            return nickname;
        } else {
            return "Email not found";
        }
    }

    @PostMapping("/reset-password/email/verify")
    public @ResponseBody String resetPasswordEmailVerify(@RequestBody Map<String, String> jsonParam) {
        String email = jsonParam.get("email");
        String response = gamerService.verifyEmail(email);
        return response;
    }

    @PostMapping("/reset-password/email/verify/check")
    public @ResponseBody String checkResetPasswordEmail(@RequestBody Map<String, String> jsonParam) {
        String code = jsonParam.get("code");
        String email = jsonParam.get("email");
        String verifyResult = gamerService.checkEmailVerifyCode(email, code);
        return verifyResult;
    }

    @PostMapping("/reset-password/email/verify/check/reset")
    public String resetPassword(@RequestBody Map<String, String> jsonParam) {
        String email = jsonParam.get("email");
        String newPassword = jsonParam.get("newPassword");

        boolean resetResult = gamerService.resetPassword(email, newPassword);
        if (resetResult) {
            return "Success";
        } else {
            return "Fail";
        }
    }

    @GetMapping("/whoAmI")
    public Map<String, String> whoAmI(Principal principal) {
        Gamer gamer = gamerService.findByNickname(principal.getName());
        return Map.of("nickname", gamer.getNickname(), "winScore", String.valueOf(gamer.getWin()) ,
                "loseScore", String.valueOf(gamer.getLose()), "drawScore", String.valueOf(gamer.getDraw()),
                "tier", gamer.getTier(), "uuid", gamer.getId().toString());
    }


    @GetMapping("/ranking")
    public List<RankDTO> getGamers() {
        List<Gamer> gamers = gamerService.getGamerOrderByRankPoint();

        return gamers.stream().map(gamer -> new RankDTO(gamer.getNickname(), gamer.getWin(), gamer.getLose(), gamer.getDraw(), gamer.getRankPoint(), gamer.getTier())).toList();
    }

}
