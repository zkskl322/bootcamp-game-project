package springboot.profpilot.model.Gamer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.profpilot.global.Utils.GenerateRandomValue;
import springboot.profpilot.model.emailverfiy.EmailService;
import springboot.profpilot.model.emailverfiy.EmailVerify;
import springboot.profpilot.model.emailverfiy.EmailVerifyService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;
    private final EmailVerifyService emailVerifyService;
    private final PasswordEncoder passwordEncoder;

    public Gamer save(String email, String nickname, String realname, String password) {
        Gamer user = new Gamer();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        user.setRealname(realname);
        user.setCreateDate(LocalDateTime.now());
        user.setRole("ROLE_USER");
        return gamerRepository.save(user);
    }

    public boolean login(String email, String password) {
        Gamer gamer = gamerRepository.findByEmail(email);
        if (gamer != null && passwordEncoder.matches(password, gamer.getPassword())) {
            return true;
        }
        return false;
    }

    public String verifyEmail(String email) {
        GenerateRandomValue generateRandomValue = new GenerateRandomValue();
        String code = generateRandomValue.getRandomPassword(10);

        EmailVerify emailVerfiy = new EmailVerify();
        emailVerfiy.setEmail(email);
        emailVerfiy.setCode(code);
        emailVerfiy.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        emailVerfiy.setVerified(false);
        emailVerifyService.save(emailVerfiy);

        return EmailService.sendEmailVerifyCode(email, code);
    }

    public String checkEmailVerifyCode(String email, String code) {
        EmailVerify emailVerfiy = emailVerifyService.findByEmail(email);
        if (emailVerfiy == null) {
            return "notfound";
        }
        if (emailVerfiy.getCode().equals(code)) {
            emailVerfiy.setVerified(true);
            emailVerifyService.save(emailVerfiy);
            return "success";
        } else {
            return "fail";
        }
    }

    public String findNicknameByEmail(String email) {
        Gamer gamer = gamerRepository.findByEmail(email);
        if (gamer != null) {
            return gamer.getNickname();
        }
        return null;
    }

    public boolean resetPassword(String email, String newPassword) {
        Gamer gamer = gamerRepository.findByEmail(email);
        if (gamer != null) {
            gamer.setPassword(passwordEncoder.encode(newPassword));
            gamerRepository.save(gamer);
            return true;
        }
        return false;
    }

    public Gamer findByNickname(String nickname) {
        return gamerRepository.findByNickname(nickname);
    }

//    public List<GamerDTO> getGamerData() {
//        List<Gamer> gamers = gamerRepository.findAll();
//        return gamers.stream()
//                .map(gamer -> new GamerDTO(
//                        gamer.getNickname(),
//                        gamer.getPassword(),
//                        gamer.getName(),
//                        gamer.getEmail()
//                )).collect(Collectors.toList());
//    }

}
