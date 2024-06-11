package springboot.profpilot.model.emailverfiy;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    EmailVerify findByEmail(String email);

    @Modifying
    @Transactional
    @Query("delete from EmailVerify e where e.email = ?1")
    void deleteByEmail(String email);
}

