package springboot.profpilot.model.Gamer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
    Gamer findByRealname(String realname);
    Gamer findByEmail(String email);
    Gamer findByNickname(String nickname);
    Optional<Gamer> findByLoginId(String loginId);

    Optional<Gamer> findByUsername(String username);
//    Optional<Gamer> findByEmail(String email);
}
