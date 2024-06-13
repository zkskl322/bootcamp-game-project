package springboot.profpilot.model.Gamer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
    Gamer findByRealname(String realname);
    Gamer findByEmail(String email);
    Gamer findByNickname(String nickname);
}
