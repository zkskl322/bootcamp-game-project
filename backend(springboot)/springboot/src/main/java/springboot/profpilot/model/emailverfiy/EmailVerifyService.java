package springboot.profpilot.model.emailverfiy;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailVerifyRepository emailVerifyRepository;

    public void save(EmailVerify emailVerify) {
        emailVerifyRepository.save(emailVerify);
    }

    public EmailVerify findByEmail(String email) {
        return emailVerifyRepository.findByEmail(email);
    }

    @Transactional
    public void deleteByEmail(String email) {
        emailVerifyRepository.deleteByEmail(email);
    }
}
