package springboot.profpilot.model.Gamer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;
    private final PasswordEncoder passwordEncoder;

    public Gamer save(String email, String nickname, String name, String password) {
        Gamer user = new Gamer();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setCreateDate(LocalDateTime.now());
        return gamerRepository.save(user);
    }
    public Gamer findByName(String name) {
        return gamerRepository.findByName(name);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
