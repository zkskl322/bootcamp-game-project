package springboot.profpilot.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(String email, String nickname, String name, String password) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setCreateDate(LocalDateTime.now());
        return userRepository.save(user);
    }
}
