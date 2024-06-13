package springboot.profpilot.model.Gamer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.profpilot.model.emailverfiy.EmailService;
import springboot.profpilot.model.emailverfiy.EmailVerify;
import springboot.profpilot.model.emailverfiy.EmailVerifyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;
    private final EmailVerifyRepository emailVerifyRepository;
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

    public Gamer findByNickname(String nickname) {
        return gamerRepository.findByNickname(nickname);
    }

    public void sendVerificationCode(String email) {
        String code = EmailService.sendEmailVerifyCode(email);
        if (code != null) {
            EmailVerify emailVerify = new EmailVerify();
            emailVerify.setEmail(email);
            emailVerify.setCode(code);
            emailVerify.setVerified(false);
            emailVerify.setCreateTime(LocalDateTime.now());
            emailVerifyRepository.save(emailVerify);
        }
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
